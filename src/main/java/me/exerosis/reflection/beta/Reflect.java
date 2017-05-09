package me.exerosis.reflection.beta;

import me.exerosis.reflection.exceptions.notfound.FieldNotFoundException;

public class Reflect {

    public static <Type> ReflectField<Type> Field(Class<?> parent, ReflectClass<Type> type) throws FieldNotFoundException {
        this(parent, type.toClass(), 0);
    }

    public static <Type> ReflectField<Type> Field(Class<?> parent, Class<Type> type) throws FieldNotFoundException {
        this(parent, type, 0);
    }

    public static <Type> ReflectField<Type> Field(Class<?> parent, Class<Type> type, int position) throws FieldNotFoundException {
        try {
            return new ReflectField<>(FieldUtilities.findField(parent, type, position));
        } catch (NoSuchFieldException exception) {
            throw new FieldNotFoundException(exception, parent, type, position);
        }
    }

    public static <Type> ReflectField<Type> Field(Class<?> parent, String name) throws FieldNotFoundException {
        try {
            return new ReflectField<>(parent.getField(name));
        } catch (NoSuchFieldException exception) {
            throw new FieldNotFoundException(exception, parent, name);
        }
    }


}