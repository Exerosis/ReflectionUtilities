package me.exerosis.reflection;

import me.exerosis.reflection.exceptions.notfound.ConstructorNotFoundException;
import me.exerosis.reflection.exceptions.notfound.FieldNotFoundException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class ReflectClass<T> {
    private Class<?> clazz;
    private T instance;
    private List<ReflectMethod> allMethods = new ArrayList<>();
    private List<ReflectField<Object>> allFields = new ArrayList<>();
    private List<Constructor<?>> allConstructors = new ArrayList<>();

    protected ReflectClass(Class<?> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("Cannot create a null instance of ReflectClass!");
        this.clazz = clazz;
        fillLists();
    }

    protected ReflectClass(T instance) {
        if (instance == null)
            throw new IllegalArgumentException("Cannot create a null instance of ReflectClass!");
        this.instance = instance;
        clazz = instance.getClass();
        fillLists();
    }

    private void fillLists() {
        allConstructors.addAll(Arrays.asList(clazz.getConstructors()));
        allConstructors.addAll(Arrays.asList(clazz.getDeclaredConstructors()));

        for (Method method : clazz.getMethods())
            allMethods.add(new ReflectMethod(method));
        for (Field field : clazz.getFields())
            allFields.add(new ReflectField<>(field, instance));

        Class<?> loopClass = clazz;
        while (loopClass != null && !loopClass.equals(Class.class)) {
            for (Method method : loopClass.getDeclaredMethods())
                allMethods.add(new ReflectMethod(method));
            for (Field field : loopClass.getDeclaredFields())
                allFields.add(new ReflectField<>(field, instance));
            loopClass = loopClass.getSuperclass();
        }
    }

    //TODO add all potential getters :D
    //Field getters.
    public <K> ReflectField<K> field(Class<K> type) {
        return field(new ReflectClass<>(type));
    }

    public <K> ReflectField<K> field(ReflectClass<K> type) {
        return field(type, 0);
    }

    public <K> ReflectField<K> field(Class<K> type, int pos) {
        return field(new ReflectClass<>(type), pos);
    }

    public <K> ReflectField<K> field(ReflectClass<K> type, int pos) {
        return field("", type, pos);
    }

    public <K> ReflectField<K> field(Class<K> type, String name) {
        return field(new ReflectClass<>(type), name);
    }

    public <K> ReflectField<K> field(ReflectClass<K> type, String name) {
        return field(name, type, -1);
    }

    public <K> ReflectField<K> field(String name) {
        return field(name, null, -1);
    }


    private <K> ReflectField<K> field(String name, ReflectClass<K> type, int pos) {
        int index = -1;
        for (ReflectField<Object> field : allFields) {
            if (field.getName().equals(name))
                return (ReflectField<K>) field;

            if (type == null)
                continue;

            /*if(CorrespondingType.isPrimitive(type)){
                if(field.getReflectType().isClassEqual(type))
            }*/
            if (type.getClazz().isAssignableFrom(field.getType())) {
                index++;
                if (index == pos)
                    return (ReflectField<K>) field;
            }
        }

        throw new FieldNotFoundException(clazz, name, type == null ? null : type.getClazz(), pos);
    }

    public <K> K value(Class<K> type) {
        return field(type).value();
    }

    public <K> K value(ReflectClass<K> type) {
        return field(type).value();
    }

    public <K> K value(Class<K> type, int pos) {
        return field(type, pos).value();
    }

    public <K> K value(ReflectClass<K> type, int pos) {
        return field(type, pos).value();
    }

    public <K> K value(Class<K> type, String name) {
        return field(type, name).value();
    }

    public <K> K value(ReflectClass<K> type, String name) {
        return field(type, name).value();
    }

    public <K> K value(String name) {
        return this.<K>field(name).value();
    }


    public <K> ReflectClass<K> access(Class<K> type) {
        return new ReflectClass<>(value(type));
    }

    public <K> ReflectClass<K> access(ReflectClass<K> type) {
        return new ReflectClass<>(value(type));
    }

    public <K> ReflectClass<K> access(Class<K> type, int pos) {
        return new ReflectClass<>(value(type, pos));
    }

    public <K> ReflectClass<K> access(ReflectClass<K> type, int pos) {
        return new ReflectClass<>(value(type, pos));
    }

    public <K> ReflectClass<K> access(Class<K> type, String name) {
        return new ReflectClass<>(value(type, name));
    }

    public <K> ReflectClass<K> access(ReflectClass<K> type, String name) {
        return new ReflectClass<>(value(type, name));
    }

    public <K> ReflectClass<K> access(String name) {
        return new ReflectClass<>(this.<K>value(name));
    }

    public Class<?>[] getGenericTypes() {
        Type[] genericTypes = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
        Class<?>[] genericClassTypes = new Class<?>[genericTypes.length];
        for (Type genericType : genericTypes) {
            System.out.println(genericType);
            //genericClassTypes[x] = (ReflectClass<?>) genericTypes[x];
        }
        return genericClassTypes;
    }

    public Constructor<?> getConstructor(int index) {
        int x = 0;
        for (Constructor<?> constructor : allConstructors)
            if (x++ == index)
                return constructor;
        throw new ConstructorNotFoundException(clazz);
    }

    public Constructor<?> getConstructor(Class<?>... paramTypes) {
        return getConstructor(0, paramTypes);
    }

    public Constructor<?> getConstructor(int index, Class<?>... paramTypes) {
        int i = 0;
        Class<?>[] types = CorrespondingType.getPrimitive(paramTypes);
        for (Constructor<?> constructor : allConstructors) {
            Class<?>[] constructorTypes = CorrespondingType.getPrimitive(constructor.getParameterTypes());
            if (CorrespondingType.compare(types, constructorTypes)) {
                if (i++ == index)
                    return constructor;
            }

        }
        throw new ConstructorNotFoundException(clazz, types);
    }

    public T newInstance(Object... args) {
        try {
            instance = (T) getConstructor(CorrespondingType.getPrimitive(args)).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    //TODO Remake method getters to work with return type, params, and name as well as create exception for method not found!
    //ReflectMethod getters.
    public <R> ReflectMethod<R> getMethodByReturn(Class<R> returnType) {
        for (ReflectMethod method : allMethods)
            if (returnType.equals(method.getReturnType()))
                return method;
        throw new RuntimeException("ReflectMethod not found!");
    }

    public <R> ReflectMethod<R> getMethodByReturn(ReflectClass<R> returnType) {
        return getMethodByReturn((Class<R>) returnType.getClazz());
    }

    public <R> ReflectMethod<R> getMethod(Class<?>... paramTypes) {
        ReflectClass<?>[] classes = new ReflectClass[paramTypes.length];
        for (int x = 0; x < classes.length; x++)
            classes[x] = Reflect.Class(paramTypes[x]);
        return getMethod(classes);
    }

    public <R> ReflectMethod<R> getMethod(ReflectClass<?>... paramTypes) {
        Class<?>[] t = CorrespondingType.getPrimitive(paramTypes);
        for (ReflectMethod method : getMethods()) {
            Class<?>[] types = CorrespondingType.getPrimitive(method.getParameterTypes());
            if (CorrespondingType.compare(types, t))
                return method;
        }
        return null;
    }

    public <R> ReflectMethod<R> getMethod(String name, Class<?>... paramTypes) {
        Class<?>[] t = CorrespondingType.getPrimitive(paramTypes);
        for (ReflectMethod method : getMethods()) {
            Class<?>[] types = CorrespondingType.getPrimitive(method.getParameterTypes());
            if (method.getName().equals(name) && CorrespondingType.compare(types, t))
                return method;
        }
        return null;
    }

    public <R> ReflectMethod<R> getMethod(String name) {
        for (ReflectMethod method : getMethods())
            if (method.getName().equals(name))
                return method;
        return null;
    }

    public boolean isInstance(Object object) {
        return clazz.isInstance(object);
    }

    //Fields and methods.
    public List<ReflectMethod> getMethods() {
        List<ReflectMethod> methods = new ArrayList<>();
        for (Method method : clazz.getMethods())
            methods.add(new ReflectMethod(method, instance));
        return methods;
    }

    public List<ReflectField<Object>> getFields() {
        List<ReflectField<Object>> fields = new ArrayList<>();
        for (Field field : clazz.getFields())
            fields.add(new ReflectField<>(field, instance));
        return fields;
    }

    //Declared fields and methods.
    public List<ReflectMethod> getDeclaredMethods() {
        List<ReflectMethod> declaredMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods())
            declaredMethods.add(new ReflectMethod(method, instance));
        return declaredMethods;
    }

    public List<ReflectField<Object>> getDeclaredFields() {
        List<ReflectField<Object>> declaredFields = new ArrayList<>();
        for (Field field : clazz.getFields())
            declaredFields.add(new ReflectField<>(field, instance));
        return declaredFields;
    }

    //All fields and methods
    public List<ReflectMethod> getAllMethods() {
        return allMethods;
    }

    public List<ReflectField<Object>> getAllFields() {
        return allFields;
    }

    public Object getInstance() {
        return instance;
    }

    public ReflectClass<T> setInstance(T instance) {
        this.instance = instance;
        return this;
    }

    public Class<T> getClazz() {
        return (Class<T>) clazz;
    }

    public Package getPackage() {
        return clazz.getPackage();
    }

    public boolean isClassEqual(Class<?> otherClass) {
        return clazz.equals(otherClass);
    }

    public boolean isClassEqual(ReflectClass<?> otherClass) {
        return clazz.equals(otherClass.getClazz());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReflectClass) {
            Class clazz = ((ReflectClass) obj).getClazz();
            Object instance = ((ReflectClass) obj).getInstance();
            return this.clazz.equals(clazz) && (this.instance == null || (instance == null || this.instance.equals(instance)));
        }
        return false;
    }

    public T cast(Object object) {
        return (T) object;
    }
}