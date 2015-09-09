package me.exerosis.reflection.pool;

import java.lang.annotation.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class InstancePool extends LinkedList<Object> {
    private static final long serialVersionUID = -9144248399371451581L;

    public void addIfNotPresent(Object object) {
        if (!contains(object))
            add(object);
    }

    public void addAllIfNotPresent(Collection<?> objects) {
        objects.forEach(this::addIfNotPresent);
    }

    public void addAndSetFields(Object obj) {
        super.add(obj);
        setFields(obj);
    }

    public boolean addAllSetFields(Collection<?> paramCollection) {
        if (super.addAll(paramCollection)) {
            paramCollection.forEach(this::setFields);
            return true;
        }
        return false;
    }

    public void setFields(Object object) {
        Class<?> objectClass = object.getClass();

        for (Field field : getAllFields(object.getClass())) {
            Depend depend = field.getAnnotation(Depend.class);
            if (depend == null)
                continue;
            Class<?> originalType = field.getType();

            Class<?> type;

            boolean isArray = false;

            if (originalType.isArray()) {
                isArray = true;
                type = originalType.getComponentType();
            } else
                type = originalType;

            List<Object> toSet = new ArrayList<>();

            for (Object val : this) {
                if (type.isInstance(val))
                    toSet.add(val);
            }

            if (toSet.isEmpty()) {
                if (depend.value())
                    throw new RuntimeException("Could not find dependency " + type.getSimpleName() + " for the class " + objectClass.getSimpleName() + "!");
                if (toSet.size() > 0)
                    toSet.set(0, null);
                else
                    toSet.add(null);
                isArray = false;
            }

            try {
                field.setAccessible(true);

                if (isArray) {
                    Object array = Array.newInstance(type, toSet.size());
                    toSet.toArray((Object[]) array);
                    field.set(object, originalType.cast(array));
                } else
                    field.set(object, toSet.get(0));
                field.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public <T> ArrayList<T> getInstances(Class<T> clazz) {
        ArrayList<T> list = new ArrayList<>();
        for (Object o : this)
            if (clazz.isInstance(o))
                list.add(clazz.cast(o));
        return list;
    }

    public void setAllFields() {
        for (Object object : this)
            setFields(object);
    }

    @SuppressWarnings("static-method")
    public List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Depend {
        /**
         * True is a hard depend, shuts down the server if the instance pool
         * does not contain this class type. Default true.
         *
         * @return Hard or soft depend.
         */
        boolean value() default true;
    }
}
