package me.exerosis.reflection.exceptions.node;

/**
 * Created by The Exerosis on 8/17/2015.
 */
public class ExceptionBuilder {
    private StringBuilder builder = new StringBuilder("\t");

    public ExceptionBuilder(String name, ExceptionNode... nodes) {
        builder.append(name);
        for (ExceptionNode node : nodes)
            appendNode(node);
    }

    public ExceptionBuilder appendNode(ExceptionNode node) {
        if (node == null)
            return this;
        builder.append("\n\t");
        builder.append(node.getName());
        builder.append(": '");
        builder.append(node.getValue());
        builder.append('\'');
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
