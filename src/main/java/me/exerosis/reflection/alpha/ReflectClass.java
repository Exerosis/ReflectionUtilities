package me.exerosis.reflection.alpha;

import me.exerosis.reflection.exceptions.InstantiationFailedException;
import me.exerosis.reflection.exceptions.notfound.ConstructorNotFoundException;

import java.lang.reflect.Constructor;

import static me.exerosis.reflection.CorrespondingType.getClassList;

public interface ReflectClass<ClassType> {

    //--Constructor--
    Constructor<ClassType> constructor(java.lang.Class<?>... parameterTypes) throws ConstructorNotFoundException;

    ReflectClass<ClassType> construct(Object... parameters);

    default ClassType instantiate(Object... parameters) throws ConstructorNotFoundException, InstantiationFailedException {
        try {
            return constructor(getClassList(parameters)).newInstance(parameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InstantiationFailedException();
        }
    }

    //--ReflectField--
    <FieldType> ReflectField<FieldType, ClassType> field(String name);

    <FieldType> ReflectField<FieldType, ClassType> field(int index);

    <FieldType> ReflectField<FieldType, ClassType> field(java.lang.Class<FieldType> type, int index);

    default <FieldType> ReflectField<FieldType, ClassType> field(ReflectClass<FieldType> type, int index) {
        return field(type.toClass(), index);
    }

    default <FieldType> ReflectField<FieldType, ClassType> field(java.lang.Class<FieldType> type) {
        return field(type, 0);
    }

    default <FieldType> ReflectField<FieldType, ClassType> field(ReflectClass<FieldType> type) {
        return field(type.toClass());
    }


    //--Get--
    default <FieldType> FieldType get(ClassType instance, String name) {
        return this.<FieldType>field(name).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, int index) {
        return this.<FieldType>field(index).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, java.lang.Class<FieldType> type, int index) {
        return field(type, index).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, ReflectClass<FieldType> type, int index) {
        return field(type, index).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, java.lang.Class<FieldType> type) {
        return field(type).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, ReflectClass<FieldType> type) {
        return field(type).get(instance);
    }


    //--Set--
    default void set(ClassType instance, Object value, String name) {
        field(name).set(instance, value);
    }

    default void set(ClassType instance, Object value, int index) {
        field(index).set(instance, value);
    }

    default <FieldType> void set(ClassType instance, FieldType value, java.lang.Class<FieldType> type, int index) {
        field(type, index).set(instance, value);
    }

    default <FieldType> void set(ClassType instance, FieldType value, ReflectClass<FieldType> type, int index) {
        field(type, index).set(instance, value);
    }

    default <FieldType> void set(ClassType instance, FieldType value, java.lang.Class<FieldType> type) {
        field(type).set(instance, value);
    }

    default <FieldType> void set(ClassType instance, FieldType value, ReflectClass<FieldType> type) {
        field(type).set(instance, value);
    }


    //--Access--
    default <FieldType> ReflectClass<FieldType> access(String name, ClassType instance) {
        return this.<FieldType>field(name).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(int index, ClassType instance) {
        return this.<FieldType>field(index).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(java.lang.Class<FieldType> type, int index, ClassType instance) {
        return field(type, index).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(ReflectClass<FieldType> type, int index, ClassType instance) {
        return field(type, index).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(java.lang.Class<FieldType> type, ClassType instance) {
        return field(type).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(ReflectClass<FieldType> type, ClassType instance) {
        return field(type).access(instance);
    }


    //--ReflectMethod--
    //Minimal
    <ReturnType> ReflectMethod<ReturnType> method(int position);

    <ReturnType> ReflectMethod<ReturnType> method(String name);

    <ReturnType> ReflectMethod<ReturnType> method(int position, java.lang.Class<ReturnType> type);

    //Faster
    <ReturnType> ReflectMethod<ReturnType> method(String name, java.lang.Class<ReturnType> type);

    <ReturnType> ReflectMethod<ReturnType> method(int position, String name, java.lang.Class<ReturnType> type);

    <ReturnType> ReflectMethod<ReturnType> method(int position, String name);

    //--Call--
    default <ReturnType> ReflectClass<ReturnType> call(int position, Object instance, Object... parameters) {
        return this.<ReturnType>method(position).call(instance, parameters);
    }

    default <ReturnType> ReflectClass<ReturnType> call(String name, Object instance, Object... parameters) {
        return this.<ReturnType>method(name).call(instance, parameters);
    }

    default <ReturnType> ReflectClass<ReturnType> call(int position, java.lang.Class<ReturnType> type, Object instance, Object... parameters) {
        return method(position, type).call(instance, parameters);
    }

    default <ReturnType> ReflectClass<ReturnType> call(String name, java.lang.Class<ReturnType> type, Object instance, Object... parameters) {
        return method(name, type).call(instance, parameters);
    }


    //--Invoke--


    //--General--
    java.lang.Class<ClassType> toClass();
}