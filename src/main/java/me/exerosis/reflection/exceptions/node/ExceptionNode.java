package me.exerosis.reflection.exceptions.node;

/**
 * Created by The Exerosis on 8/17/2015.
 */
public class ExceptionNode {
    private String name = "";
    private Object value;

    public ExceptionNode(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
