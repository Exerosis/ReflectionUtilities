package me.exerosis.reflection.alpha.implementation;

import me.exerosis.reflection.alpha.ReflectClass;
import me.exerosis.reflection.alpha.ReflectField;
import me.exerosis.reflection.alpha.ReflectMethod;
import me.exerosis.reflection.exceptions.notfound.ConstructorNotFoundException;

import java.lang.reflect.Constructor;

public class ReflectClassImplementation<ClassType> implements ReflectClass<ClassType> {
    private Class<ClassType> type;

    public ReflectClassImplementation(Class<ClassType> type) {
        this.type = type;
    }

    //--Constructor--
    @Override
    public Constructor<ClassType> constructor(Class<?>[] parameterTypes) throws ConstructorNotFoundException {
        return null;
    }

    @Override
    public ReflectClass<ClassType> construct(Object... parameters) {
        return null;
    }

    //--Field--
    @Override
    public <FieldType> ReflectField<FieldType, ClassType> field(String name) {
        return type.getField(name);
    }

    @Override
    public <FieldType> ReflectField<FieldType, ClassType> field(int index) {
        return null;
    }

    @Override
    public <FieldType> ReflectField<FieldType, ClassType> field(Class<FieldType> type, int index) {
        return null;
    }

    //--Method--
    @Override
    public <ReturnType> ReflectMethod<ReturnType> method(int position) {
        return null;
    }

    @Override
    public <ReturnType> ReflectMethod<ReturnType> method(String name) {
        return null;
    }

    @Override
    public <ReturnType> ReflectMethod<ReturnType> method(int position, Class<ReturnType> type) {
        return null;
    }

    @Override
    public <ReturnType> ReflectMethod<ReturnType> method(String name, Class<ReturnType> type) {
        return null;
    }

    @Override
    public <ReturnType> ReflectMethod<ReturnType> method(int position, String name, Class<ReturnType> type) {
        return null;
    }

    @Override
    public <ReturnType> ReflectMethod<ReturnType> method(int position, String name) {
        return null;
    }


    //--General--
    @Override
    public Class<ClassType> toClass() {
        return type;
    }
}
