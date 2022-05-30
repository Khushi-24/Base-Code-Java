package com.demo.BaristaBucks.Common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response {

    private String message;
    private Integer status;
    private Object data;
    private MessageResponseDto[] errors;

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public Response(String message, Integer status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Response(MessageResponseDto[] errors, String message) {
        this.message = message;
        this.errors = errors;
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response() {
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MessageResponseDto[] getErrors() {
        return errors;
    }

    public void setErrors(MessageResponseDto[] errors) {
        this.errors = errors;
    }
}
