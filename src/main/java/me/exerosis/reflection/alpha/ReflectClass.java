package me.exerosis.reflection.alpha;

import me.exerosis.reflection.exceptions.notfound.FieldNotFoundException;

import java.lang.reflect.Field;

public class ReflectClass<Type> {
    private Class<Type> type;
    private Type instance;

    @SuppressWarnings("unchecked")
    public ReflectClass(Type instance) {
        this((Class<Type>) instance.getClass());
        this.instance = instance;
    }

    public ReflectClass(Class<Type> type) {
        this.type = type;
    }

    public <FieldType> ReflectField<FieldType> field(me.exerosis.reflection.beta.ReflectClass<FieldType> type) {
        return field(type.toClass(), 0);
    }

    //Primary Methods
    public <FieldType> ReflectField<FieldType> field(me.exerosis.reflection.beta.ReflectClass<FieldType> type, int position) {
        return field(type.toClass(), position);
    }

    public <FieldType> ReflectField<FieldType> field(Class<FieldType> type, int position) {
        return type;
    }

    public <FieldType> ReflectField<FieldType> field(String name) {
        return new ReflectField<>(name);
    }

    public class ReflectField<FieldType> {
        private Class<?> fieldType;
        private final Field field;

        public ReflectField(Class<FieldType> type, int position) {
            fieldType = type;
            field = null;
        }

        public ReflectField(String name) {
            try {
                field = type.getField(name);
            } catch (NoSuchFieldException exception) {
                throw new FieldNotFoundException(exception, type, name);
            }
        }


        public ReflectField(Class<?> parent, Field field) {
            this.parent = parent;
            this.field = field;
        }

        public Type access()

        @SuppressWarnings("unchecked")
        public Class<Type> getType() {
            return type == null ? field = (Class<Type>) field.getType() : field;
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
}
