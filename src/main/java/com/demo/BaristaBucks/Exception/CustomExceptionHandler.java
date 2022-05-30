package com.demo.BaristaBucks.Exception;

import com.demo.BaristaBucks.Common.MessageResponseDto;
import com.demo.BaristaBucks.Common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     * Intercept and process constraint violations for proper error reporting
     */
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        List<MessageResponseDto> errors = new ArrayList<>();
        if (errorList.size() > 0)
            for (ObjectError error : errorList)
                errors.add(new MessageResponseDto(error.getDefaultMessage()));
        MessageResponseDto[] errorsArray = new MessageResponseDto[errorList.size()];
        return new ResponseEntity<>(new Response(errors.toArray(errorsArray), errors.get(0).getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public final ResponseEntity<Object> handleHibernateConstraintViolationException(Exception ex, WebRequest request) {
        org.hibernate.exception.ConstraintViolationException cause = (org.hibernate.exception.ConstraintViolationException) ex.getCause();
        String errorMessage = cause.getCause().getMessage();
        //constraint name is null for "null" constraint violation
        if (cause.getConstraintName() != null) {
            //in case of unique key constraint violation we don't want to
            //send unique name in response so we are removing unique key name
            //using string manipulation
            Matcher matcher = Pattern.compile("'UK\\s*(\\w+)'").matcher(errorMessage);
            if (matcher.find()) {
                String toReplace = matcher.group(0);
                log.info(matcher.group(0));
                errorMessage = errorMessage.replaceAll(toReplace, "");
                log.info(errorMessage);
            }
        }
        return ResponseEntity.badRequest().body(new Response(errorMessage));


    }
}
