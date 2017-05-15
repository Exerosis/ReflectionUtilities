package me.exerosis.reflection.exceptions.notfound;

import me.exerosis.reflection.exceptions.ReflectException;
import me.exerosis.reflection.exceptions.node.ExceptionBuilder;
import me.exerosis.reflection.exceptions.node.ExceptionNode;

public class FieldNotFoundException extends ReflectException {

    public FieldNotFoundException(Class<?> parent, String name) {
        this(null, parent, name);
    }

    public FieldNotFoundException(Class<?> parent, Class<?> type) {
        this(null, parent, type);
    }

    public FieldNotFoundException(Class<?> parent, Class<?> type, int position) {
        this(null, parent, type, position);
    }
    
    public FieldNotFoundException(NoSuchFieldException exception, Class<?> parent, String name) {
        this(exception, parent, name, null, -1);
    }

    public FieldNotFoundException(NoSuchFieldException exception, Class<?> parent, Class<?> type) {
        this(exception, parent, null, type, -1);
    }

    public FieldNotFoundException(NoSuchFieldException exception, Class<?> parent, Class<?> type, int position) {
        this(exception, parent, null, type, position);
    }

    private FieldNotFoundException(NoSuchFieldException exception, Class<?> parent, String name, Class<?> type, int position) {
        super(new ExceptionBuilder("Field not found!", getExceptionNodes(parent, name, type, position)));
        if (exception != null)
            setStackTrace(exception.getStackTrace());
    }

    private static ExceptionNode[] getExceptionNodes(Class<?> parent, String name, Class<?> type, int position) {
        ExceptionNode[] nodes = new ExceptionNode[4];
        if (parent != null)
            nodes[0] = new ExceptionNode("ReflectClass", parent.getSimpleName());
        if (type != null)
            nodes[2] = new ExceptionNode("Type", type.getSimpleName());

        nodes[1] = new ExceptionNode("Name", name);
        nodes[3] = new ExceptionNode("Position", position == -1 ? "Not Specified" : position);
        return nodes;
    }
}