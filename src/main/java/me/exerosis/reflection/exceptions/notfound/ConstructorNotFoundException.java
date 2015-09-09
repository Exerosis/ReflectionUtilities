package me.exerosis.reflection.exceptions.notfound;

import me.exerosis.reflection.exceptions.ReflectException;
import me.exerosis.reflection.exceptions.node.ExceptionBuilder;
import me.exerosis.reflection.exceptions.node.ExceptionNode;

/**
 * Created by The Exerosis on 8/17/2015.
 */
public class ConstructorNotFoundException extends ReflectException {

    public ConstructorNotFoundException(Class<?> clazz, Class<?>... params) {
        super(new ExceptionBuilder("Constructor not found!", getExceptionNodes(clazz, params)));
    }

    private static ExceptionNode[] getExceptionNodes(Class<?> clazz, Class<?>... params) {
        ExceptionNode[] nodes = new ExceptionNode[2];
        if (clazz != null)
            nodes[0] = new ExceptionNode("Class", clazz.getSimpleName());

        if (params.length == 0)
            return nodes;
        String paramsString = "\n";
        for (Class<?> param : params)
            paramsString += param.getSimpleName() + "\n\t";

        nodes[1] = new ExceptionNode("Params", paramsString);
        return nodes;
    }
}