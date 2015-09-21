package me.exerosis.reflection;

import java.lang.reflect.Method;

public class ReflectMethod {
    private Method method;
    private Object instance;

    public ReflectMethod(Method method) {
        this(method, null);
    }

    public ReflectMethod(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
        method.setAccessible(true);
    }

    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    public ReflectClass<Object> getDeclaringClass() {
        return new ReflectClass<>(method.getDeclaringClass());
    }

    public ReflectClass<Object> getReturnClass() {
        return new ReflectClass<>(method.getReturnType());
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Object callStatic(Object... params) {
        return callInstance(null, params);
    }

    public Object callInstance(Object instance, Object... params) {
        try {
            return method.invoke(instance, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object call(Object... params) {
        return callInstance(instance, params);
    }

    public String getName() {
        return method.getName();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }
}