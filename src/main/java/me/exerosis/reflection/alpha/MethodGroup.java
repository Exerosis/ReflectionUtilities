package me.exerosis.reflection.alpha;

import me.exerosis.reflection.CorrespondingType;

import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public interface MethodGroup<ReturnType> {

    Method[] getImplementations();

    default Method implementation(Object... parameterTypes) {
        return implementation(CorrespondingType.getPrimitive(parameterTypes));
    }

    default Method implementation(java.lang.Class... parameterTypes) {
        for (Method method : getImplementations()) {
            if (method.getParameterCount() == parameterTypes.length &&
                    CorrespondingType.compare(parameterTypes, method.getParameterTypes()))
                return method;
        }
        //TODO Replace
        throw new RuntimeException();
    }

    default ReturnType invokeStatic(Object... parameters) {
        return invoke(null, parameters);
    }

    default ReturnType invoke(Object instance, Object... parameters) {
        try {
            return (ReturnType) implementation(parameters).invoke(instance, parameters);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ReflectClass<ReturnType> call(Object instance, Object... parameters);

    default ReflectClass<ReturnType> callStatic(Object... parameters) {
        return call(null, parameters);
    }
}
