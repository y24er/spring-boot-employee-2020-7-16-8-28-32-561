package com.thoughtworks.springbootemployee.exception;

public class NotFoundEmployeeException extends RuntimeException{
    public String getMessage() {
        return "Not exist this employee!";
    }
}
