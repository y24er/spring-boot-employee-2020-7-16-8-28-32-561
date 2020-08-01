package com.thoughtworks.springbootemployee.Exception;

public class NotFoundCompanyException extends RuntimeException {
    public String getMessage() {
        return "Not exist this company!";
    }
}
