package me.exerosis.reflection.alpha.implementation;

import me.exerosis.reflection.alpha.ReflectClass;
import me.exerosis.reflection.alpha.ReflectField;

import java.lang.reflect.Field;

public class ReflectFieldImplementation<FieldType, ClassType> implements ReflectField<FieldType, ClassType> {
    @Override
    public Field toField() {
        return null;
    }

    @Override
    public ReflectClass<FieldType> access(ClassType instance) {
        return null;
    }
}
