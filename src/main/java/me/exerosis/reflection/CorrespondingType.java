package me.exerosis.reflection;

import java.util.HashMap;
import java.util.Map;

// Utility methods.
public enum CorrespondingType {
    /**
     * Reference to primitive datatype pairs.
     */
    BYTE(byte.class, Byte.class),
    SHORT(short.class, Short.class),
    INTEGER(int.class, Integer.class),
    LONG(long.class, Long.class),
    CHARACTER(char.class, Character.class),
    FLOAT(float.class, Float.class),
    DOUBLE(double.class, Double.class),
    BOOLEAN(boolean.class, Boolean.class);

    static final Map<Class<?>, CorrespondingType> CORRESPONDING_TYPES = new HashMap<>();

    static {
        for (CorrespondingType type : CorrespondingType.values()) {
            CORRESPONDING_TYPES.put(type.getPrimitive(), type);
            CORRESPONDING_TYPES.put(type.getReference(), type);
        }
    }

    private final Class<?> primitive;
    private final Class<?> reference;

    /**
     * Construct a new {@linkplain CorrespondingType}.
     *
     * @param primitive {@linkplain Class}<?> - Primitive {@linkplain Class}<?> of this datatype.
     * @param reference {@linkplain Class}<?> - Reference {@linkplain Class}<?> of this datatype.
     */
    CorrespondingType(Class<?> primitive, Class<?> reference) {
        this.primitive = primitive;
        this.reference = reference;
    }

    /**
     * Returns the datatype with the given primitive/reference {@linkplain Class}<?>.
     *
     * @param clazz {@linkplain Class}<?> - Primitive/Reference {@linkplain Class}<?> of the datatype.
     * @return {@linkplain CorrespondingType} - The pair of primitive and reference types.
     */
    public static CorrespondingType fromClass(Class<?> clazz) {
        return CORRESPONDING_TYPES.get(clazz);
    }

    /**
     * Returns the primitive {@linkplain Class}<?> of the datatype with the given reference {@linkplain Class}<?>.
     *
     * @param clazz {@linkplain Class}<?> - Reference class of the datatype.
     * @return {@linkplain Class}<?> - The primitive class created from the reference {@linkplain Class}<?>.
     */
    public static Class<?> getPrimitive(Class<?> clazz) {
        CorrespondingType type = fromClass(clazz);
        return type == null ? clazz : type.getPrimitive();
    }

    /**
     * Returns the primitive class array of the given class array.
     *
     * @param classes {@linkplain Class}<?>[] - The primitive class to be converted.
     * @return {@linkplain Class}<?>[] - The primitive {@linkplain Class}<?> array created by the {@linkplain Class}<?> array.
     */
    public static Class<?>[] getPrimitive(Class<?>[] classes) {
        int length = classes == null ? 0 : classes.length;
        Class<?>[] types = new Class<?>[length];
        for (int index = 0; index < length; index++)
            types[index] = getPrimitive(classes[index]);
        return types;
    }


    //Utility methods.

    /**
     * Returns the primitive class array of the given object array.
     *
     * @param objects {@linkplain Object}[] - The {@linkplain Object} array to be converted.
     * @return {@linkplain Class}<?>[] - The primitive {@linkplain Class}<?> array created by the {@linkplain Object} array.
     */
    public static Class<?>[] getPrimitive(Object[] objects) {
        int length = objects == null ? 0 : objects.length;
        Class<?>[] types = new Class<?>[length];
        for (int index = 0; index < length; index++) {
            Object object = objects[index];
            types[index] = object == null ? null : getPrimitive(object.getClass());
        }
        return types;
    }

    /**
     * Returns the reference {@linkplain Class}<?> of the datatype with the given primitive {@linkplain Class}.
     *
     * @param clazz {@linkplain Class}<?> - The primitive class to be converted.
     * @return {@linkplain Class}<?> - The reference {@linkplain Class}.
     */
    public static Class<?> getReference(Class<?> clazz) {
        CorrespondingType type = fromClass(clazz);
        return type == null ? clazz : type.getReference();
    }

    /**
     * Returns the reference {@linkplain Class}<?> array of the given {@linkplain Class}<?> array.
     *
     * @param classes {@linkplain Class}<?>[] - The {@linkplain Class}<?> array to be checked.
     * @return {@linkplain Class}<?>[] - The reference {@linkplain Class}<?> array created by the {@linkplain Class}<?> array.
     */
    public static Class<?>[] getReference(Class<?>[] classes) {
        int length = classes == null ? 0 : classes.length;
        Class<?>[] types = new Class<?>[length];
        for (int index = 0; index < length; index++)
            types[index] = getReference(classes[index]);
        return types;
    }

    /**
     * Returns the reference {@linkplain Class}<?> array of the given {@linkplain Object} array.
     *
     * @param objects {@linkplain Object}[] - The {@linkplain Object} array to be turned into the reference array.
     * @return {@linkplain Class}<?>[] - The reference {@linkplain Class}<?> array created by the {@linkplain Object} array.
     */
    public static Class<?>[] getReference(Object[] objects) {
        int length = objects == null ? 0 : objects.length;
        Class<?>[] types = new Class<?>[length];
        for (int index = 0; index < length; index++)
            types[index] = getReference(objects[index].getClass());
        return types;
    }

    /**
     * Compares two {@linkplain Class}<?> arrays on equivalence.
     *
     * @param primary   {@linkplain Class}<?>[] - The first array to be compared to the second array.
     * @param secondary {@linkplain Class}<?>[] - The second array to be compared to the first array.
     * @return boolean - Return true if the arrays are equal.
     */
    public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
        if (primary == null || secondary == null || primary.length != secondary.length)
            return false;
        for (int index = 0; index < primary.length; index++) {
            Class<?> primaryClass = primary[index];
            Class<?> secondaryClass = secondary[index];
            if (primaryClass == null && !secondaryClass.equals(Object.class))
                return false;
            if (secondaryClass == null && !primaryClass.equals(Object.class))
                return false;
            if (primaryClass != null && secondaryClass != null)
                if (primaryClass.equals(secondaryClass) || primaryClass.isAssignableFrom(secondaryClass))
                    continue;
            return false;
        }
        return true;
    }

    /**
     * Checks a {@linkplain Class}<?> is a primitive datatype.
     *
     * @param clazz {@linkplain Class}<?> - The {@linkplain Class}<?> to be checked.
     * @return boolean - True if the {@linkplain Class}<?> is a primitive datatype.
     */
    public static boolean isPrimitive(Class<?> clazz) {
        for (CorrespondingType type : CORRESPONDING_TYPES.values()) {
            if (type.getPrimitive().equals(clazz))
                return true;
        }
        return false;
    }

    /**
     * Checks a {@linkplain ReflectClass}<?> is a primitive datatype.
     *
     * @param clazz {@linkplain ReflectClass}<?> - The {@linkplain ReflectClass}<?> to be checked.
     * @return boolean - True if the {@linkplain ReflectClass}<?> is a primitive datatype.
     */
    public static boolean isPrimitive(ReflectClass<?> clazz) {
        for (CorrespondingType type : CORRESPONDING_TYPES.values()) {
            if (type.getPrimitive().equals(clazz.getClazz()))
                return true;
        }
        return false;
    }

    /**
     * Checks a {@linkplain Class}<?> is a reference type.
     *
     * @param clazz {@linkplain Class}<?> - The {@linkplain Class}<?> to be checked.
     * @return boolean - True if the {@linkplain Class}<?> is a reference datatype.
     */
    public static boolean isReference(Class<?> clazz) {
        return !isPrimitive(clazz);
    }

    /**
     * Checks a {@linkplain ReflectClass}<?> is a reference type.
     *
     * @param clazz {@linkplain ReflectClass}<?> - The {@linkplain ReflectClass}<?> to be checked.
     * @return boolean - True if the {@linkplain ReflectClass}<?> is a reference datatype.
     */
    public static boolean isReference(ReflectClass<?> clazz) {
        return !isPrimitive(clazz);
    }

    /**
     * Returns the primitive {@linkplain Class}<?> of this datatype.
     *
     * @return {@linkplain Class}<?> - The primitive {@linkplain Class}<?>.
     */
    public Class<?> getPrimitive() {
        return primitive;
    }

    /**
     * Returns the reference class of this {@linkplain CorrespondingType}.
     *
     * @return {@linkplain Class}<?> - The reference {@linkplain Class}<?>.
     */
    public Class<?> getReference() {
        return reference;
    }
}