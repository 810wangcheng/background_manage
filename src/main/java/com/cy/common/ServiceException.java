package com.cy.common;

public class ServiceException extends RuntimeException{

    public ServiceException(){
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
