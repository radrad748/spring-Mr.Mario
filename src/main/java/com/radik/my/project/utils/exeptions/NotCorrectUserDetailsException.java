package com.radik.my.project.utils.exeptions;

public class NotCorrectUserDetailsException extends RuntimeException{
    public NotCorrectUserDetailsException(String message) {
        super(message);
    }
}
