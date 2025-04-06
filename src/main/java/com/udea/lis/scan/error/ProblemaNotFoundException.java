package com.udea.lis.scan.error;

public class ProblemaNotFoundException extends RuntimeException{
    public ProblemaNotFoundException(String message) {
        super(message);
    }
}