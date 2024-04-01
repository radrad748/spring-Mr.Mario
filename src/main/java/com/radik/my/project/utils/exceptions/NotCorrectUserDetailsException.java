package com.radik.my.project.utils.exceptions;

public class NotCorrectUserDetailsException extends RuntimeException{
    public NotCorrectUserDetailsException(String message) {
        super(message);
    }
}
