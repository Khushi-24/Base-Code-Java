package com.demo.BaristaBucks.Util;

import com.demo.BaristaBucks.Common.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static ResponseEntity<?> sendOkResponse(String message, Object data) {
        if (data == null)
            return new ResponseEntity<>(new Response(message), HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response(message, data), HttpStatus.OK);
    }

    public static ResponseEntity<?> sendCreatedResponse(String message, Object data) {
        return new ResponseEntity<>(new Response(message, data), HttpStatus.CREATED);
    }

    public static ResponseEntity<?> sendBadRequest(String message, Object data) {
        if (data == null)
            return new ResponseEntity<>(new Response(message), HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(new Response(message, data), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> sendNoContentResponse() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
