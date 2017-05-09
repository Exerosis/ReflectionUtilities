package me.exerosis.reflection.exceptions.notfound;

import me.exerosis.reflection.exceptions.ReflectException;
import me.exerosis.reflection.exceptions.node.ExceptionBuilder;
import me.exerosis.reflection.exceptions.node.ExceptionNode;

/**
 * Created by The Exerosis on 8/17/2015.
 */
public class ReflectClassNotFoundException extends ReflectException {
    public ReflectClassNotFoundException(String className) {
        super(new ExceptionBuilder("ReflectClass not found!", new ExceptionNode("ReflectClass Name", className)));
    }
}