package org.example.ioc.library.container.exceptions;


public class ContainerInjectException extends RuntimeException {
    public ContainerInjectException(String msg) {
        super(msg);
    }
}
