package com.demo.BaristaBucks.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Honey Shah
 * @since 27 Feb, 2020
 */

/**
 * made it to process roll back transaction on bad request
 * if we return bad request manually spring will not roll
 * back the current transaction so we compulsory need to
 * throw transaction else we need to put all validations
 * before saving any data to database
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message){
        super(message);
    }

}