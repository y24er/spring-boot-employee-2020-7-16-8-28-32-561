package com.thoughtworks.springbootemployee.exception;

public class NotFoundCompanyException extends RuntimeException {
    public String getMessage() {
        return "Not exist this company!";
    }
}
