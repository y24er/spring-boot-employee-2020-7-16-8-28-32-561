package com.thoughtworks.springbootemployee.Exception;

public class NotFoundEmployeeException extends RuntimeException{
    public String getMessage() {
        return "Not exist this employee!";
    }
}
