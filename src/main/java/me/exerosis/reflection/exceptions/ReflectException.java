package me.exerosis.reflection.exceptions;

import me.exerosis.reflection.exceptions.node.ExceptionBuilder;

public class ReflectException extends RuntimeException {
    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(ExceptionBuilder factory) {
        super(factory.toString());
    }
}