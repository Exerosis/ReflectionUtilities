package me.exerosis.reflection.gamma;

public interface ReflectClass<ClassType> {
    //--New Instance--

    //--Construct--


    //--Field--
    <FieldType> ReflectField<FieldType, ClassType> field(String name);

    <FieldType> ReflectField<FieldType, ClassType> field(int index);

    <FieldType> ReflectField<FieldType, ClassType> field(Class<FieldType> type, int index);

    default <FieldType> ReflectField<FieldType, ClassType> field(ReflectClass<FieldType> type, int index) {
        return field(type.toClass(), index);
    }

    default <FieldType> ReflectField<FieldType, ClassType> field(Class<FieldType> type) {
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

    default <FieldType> FieldType get(ClassType instance, Class<FieldType> type, int index) {
        return field(type, index).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, ReflectClass<FieldType> type, int index) {
        return field(type, index).get(instance);
    }

    default <FieldType> FieldType get(ClassType instance, Class<FieldType> type) {
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

    default <FieldType> void set(ClassType instance, FieldType value, Class<FieldType> type, int index) {
        field(type, index).set(instance, value);
    }

    default <FieldType> void set(ClassType instance, FieldType value, ReflectClass<FieldType> type, int index) {
        field(type, index).set(instance, value);
    }

    default <FieldType> void set(ClassType instance, FieldType value, Class<FieldType> type) {
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

    default <FieldType> ReflectClass<FieldType> access(Class<FieldType> type, int index, ClassType instance) {
        return field(type, index).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(ReflectClass<FieldType> type, int index, ClassType instance) {
        return field(type, index).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(Class<FieldType> type, ClassType instance) {
        return field(type).access(instance);
    }

    default <FieldType> ReflectClass<FieldType> access(ReflectClass<FieldType> type, ClassType instance) {
        return field(type).access(instance);
    }


    //--Method--

    //--Call--

    //--Invoke--


    //--General--
    Class<ClassType> toClass();
}