package me.exerosis.reflection.beta;

import me.exerosis.reflection.exceptions.notfound.FieldNotFoundException;

import java.lang.reflect.Field;

public class ReflectField<FieldType> {
    private Class<FieldType> type;
    private final Field field;

    public ReflectField(Field field) {
        this.field = field;
    }
    public FieldType access();

    @SuppressWarnings("unchecked")
    public Class<FieldType> getType() {
        return type == null ? type = (Class<FieldType>) field.getType() : type;
    }

    public Field toField() {
        return field;
    }

    public Class<?> getParent() {
        return parent;
    }

    public static class Builder<Type> {
        public Builder() {
        }

        public me.exerosis.reflection.beta.ReflectField.Builder<Type> name() {

        }
    }
}