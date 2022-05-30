package com.demo.BaristaBucks.Exception;

import com.demo.BaristaBucks.Util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message){
        super(message);
    }

    public AlreadyExistsException(Class<?> entityClass, Object... identifications) {
        List<Object> ids = Arrays.stream(identifications).filter(id -> {
            if (Objects.nonNull(id)) {
                if (id instanceof String) {
                    return StringUtil.nonNullNonEmpty(id.toString());
                } else {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        throw new EntityNotFoundException(entityClass, ids.toString());
    }

    public AlreadyExistsException(Class<?> entityClass, String identification) {
        super(String.format("%s is already found for parameter(s) %s",
                StringUtil.convert_to_snake_case(entityClass.getSimpleName(), null), identification));
    }
}

