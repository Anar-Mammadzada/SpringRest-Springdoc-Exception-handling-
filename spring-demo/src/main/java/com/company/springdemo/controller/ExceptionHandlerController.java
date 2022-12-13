package com.company.springdemo.controller;

import com.company.springdemo.enums.ErrorCodeEnum;
import com.company.springdemo.exceptions.CustomNotFoundException;
import com.company.springdemo.rest.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCustomException(CustomNotFoundException myRestException){
        return ErrorResponse.builder()
                .code(myRestException.getCode())
                .message(myRestException.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInputParam(MethodArgumentTypeMismatchException m){
        String paramName = m.getParameter().getParameter().getName();
        return ErrorResponse.builder()
                .code(ErrorCodeEnum.VALIDATION_ERROR.getCode())
                .message(paramName + ErrorCodeEnum.VALIDATION_ERROR.getMessage())
                .build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException m){
        String fieldName = m.getBindingResult().getFieldError().getField();
        return ErrorResponse.builder()
                .code(ErrorCodeEnum.VALIDATION_ERROR.getCode())
                .message(fieldName + ErrorCodeEnum.VALIDATION_ERROR.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknown(Exception e){
        return ErrorResponse.builder()
                .code(ErrorCodeEnum.UNKNOWN_ERROR.getCode())
                .message(ErrorCodeEnum.UNKNOWN_ERROR.getMessage())
                .build();
    }
}
