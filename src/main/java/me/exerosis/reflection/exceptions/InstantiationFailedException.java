package me.exerosis.reflection.exceptions;

import me.exerosis.reflection.exceptions.node.ExceptionBuilder;
import me.exerosis.reflection.exceptions.node.ExceptionNode;

public class InstantiationFailedException extends ReflectException {
    public InstantiationFailedException() {
        super(new ExceptionBuilder("ReflectMethod not found!", getExceptionNodes()));
    }

    private static ExceptionNode[] getExceptionNodes() {
        ExceptionNode[] nodes = new ExceptionNode[1];

        return nodes;
    }
}
