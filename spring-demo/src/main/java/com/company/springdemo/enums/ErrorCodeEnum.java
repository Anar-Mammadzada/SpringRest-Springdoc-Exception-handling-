package com.company.springdemo.enums;

public enum ErrorCodeEnum {

    EMPLOYEE_NOT_FOUND(1001,"Cannot find employee with given id"),
    VALIDATION_ERROR(1002," is not valid"),
    UNKNOWN_ERROR(1000,"Unknown error");

    private final String message;
    private final int code;

     ErrorCodeEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode(){
         return code;
    }
}
