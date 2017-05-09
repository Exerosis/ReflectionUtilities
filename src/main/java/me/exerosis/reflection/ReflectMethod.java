package me.exerosis.reflection;

import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class ReflectMethod<T> {
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

    public ReflectClass<T> getReturnClass() {
        return new ReflectClass<>(method.getReturnType());
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public T rawCallStatic(Object... params) {
        return callInstance(null, params);
    }

    public T callInstance(Object instance, Object... params) {
        try {
            return (T) method.invoke(instance, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T rawCall(Object... params) {
        return callInstance(instance, params);
    }

    public ReflectClass<T> call(Object... params) {
        return new ReflectClass<>(rawCall(params));
    }

    public ReflectClass<T> callStatic(Object... params) {
        return new ReflectClass<>(rawCallStatic(params));
    }

    public String getName() {
        return method.getName();
    }

    public Class<T> getReturnType() {
        return (Class<T>) method.getReturnType();
    }
}