package com.udea.lis.scan.error;

public class ComputadorNotFoundException extends RuntimeException {
    public ComputadorNotFoundException(String message) {
        super(message);
    }
}
