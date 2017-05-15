package me.exerosis.reflection.exceptions.notfound;

import me.exerosis.reflection.exceptions.ReflectException;
import me.exerosis.reflection.exceptions.node.ExceptionBuilder;
import me.exerosis.reflection.exceptions.node.ExceptionNode;

public class MethodNotFoundException extends ReflectException {

    private MethodNotFoundException(NoSuchMethodException exception, Class<?> parent, Class<?> returnType, String name, int position, Class<?>... parameterTypes) {
        super(new ExceptionBuilder("ReflectMethod not found!", getExceptionNodes(parent, returnType, name, position, parameterTypes)));
        if (exception != null)
            setStackTrace(exception.getStackTrace());
    }

    private static ExceptionNode[] getExceptionNodes(Class<?> parent, Class<?> returnType, String name, int position, Class<?>... parameterTypes) {
        ExceptionNode[] nodes = new ExceptionNode[5];
        if (parent != null)
            nodes[0] = new ExceptionNode("ReflectClass", parent.getSimpleName());
        if (name != null)
            nodes[1] = new ExceptionNode("Name", name);
        if (returnType != null)
            nodes[2] = new ExceptionNode("Return Type", returnType);

        nodes[4] = new ExceptionNode("Position", position == -1 ? "Not Specified" : position);

        if (parameterTypes.length == 0)
            return nodes;
        StringBuilder builder = new StringBuilder("\n");
        for (Class<?> param : parameterTypes)
            builder.append('-').append(param.getSimpleName()).append("\n\t");

        nodes[3] = new ExceptionNode("Parameter Types", builder.toString());
        return nodes;
    }
}