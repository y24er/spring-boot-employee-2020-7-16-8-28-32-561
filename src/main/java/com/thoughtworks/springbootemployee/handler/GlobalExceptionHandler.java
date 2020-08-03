package com.thoughtworks.springbootemployee.handler;

import com.thoughtworks.springbootemployee.exception.NotFoundCompanyException;
import com.thoughtworks.springbootemployee.exception.NotFoundEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundCompanyException.class)
    public @ResponseBody String notFoundCompanyExceptionHandler(NotFoundCompanyException notFoundCompanyException) {
        System.out.println(notFoundCompanyException.getMessage());
        return notFoundCompanyException.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundEmployeeException.class)
    public String notFoundEmployeeExceptionHandler(NotFoundEmployeeException notFoundEmployeeException) {
        return notFoundEmployeeException.getMessage();
    }
}
