package org.example.ioc.library.container.exceptions;

public class BeanCreationException extends RuntimeException {
    public BeanCreationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
