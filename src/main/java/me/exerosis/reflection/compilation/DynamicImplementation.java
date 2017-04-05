package me.exerosis.reflection.compilation;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DynamicImplementation {

    private DynamicImplementation() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T become(Class<T> type, Object target, Object... implementations) {
        Class<?>[] interfaces;
        if (type.isInterface())
            interfaces = new Class<?>[]{type};
        else
            interfaces = type.getInterfaces();

        return (T) Proxy.newProxyInstance(type.getClassLoader(), interfaces, (proxy, method, args) -> {
            method.setAccessible(true);
            for (Object implementation : implementations)
                for (Method declaredMethod : implementation.getClass().getDeclaredMethods())
                    if (method.getName().equals(declaredMethod.getName()) && Arrays.equals(method.getParameterTypes(), declaredMethod.getParameterTypes()))
                        return method.invoke(implementation, args);
            return method.invoke(target, args);
        });
    }
}