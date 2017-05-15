package me.exerosis.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class FieldUtilities {
    public static final Field FIELD_MODIFIERS;
    public static final Field FIELD_ACCESSOR;
    public static final Field FIELD_ACCESSOR_OVERRIDE;
    public static final Field FIELD_ROOT;
    public static final Map<Class<?>, Field> CACHE = new HashMap<>();
    public static final Map<Class<?>, Integer> REMAINING_CACHE = new HashMap<>();
    public static boolean CACHE_FIELDS = true;

    static {
        Field fieldModifiers = null;
        Field fieldAccessor = null;
        Field fieldAccessorOverride = null;
        Field fieldRoot = null;
        try {
            fieldModifiers = Field.class.getDeclaredField("modifiers");
            fieldModifiers.setAccessible(true);
            fieldAccessor = Field.class.getDeclaredField("fieldAccessor");
            fieldAccessor.setAccessible(true);
            fieldAccessorOverride = Field.class.getDeclaredField("overrideFieldAccessor");
            fieldAccessorOverride.setAccessible(true);
            fieldRoot = Field.class.getDeclaredField("root");
            fieldRoot.setAccessible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            FIELD_MODIFIERS = fieldModifiers;
            FIELD_ACCESSOR = fieldAccessor;
            FIELD_ACCESSOR_OVERRIDE = fieldAccessorOverride;
            FIELD_ROOT = fieldRoot;
        }
    }

    private FieldUtilities() {
    }

    public static Field findField(Class<?> clazz, Class<?> type, int position) throws NoSuchFieldException {
        int end = CACHE_FIELDS ? REMAINING_CACHE.getOrDefault(clazz, 0) : 0;
        int hits = -1;
        Field[] fields = clazz.getFields();
        for (int index = end; index < fields.length; index++) {
            Field field = fields[index];
            if (CACHE_FIELDS) {
                CACHE.put(field.getType(), field);
                end = index;
            }
            if (field.getType().equals(type) && hits++ >= position) {
                result = field;
                break;
            }
        }
        if (CACHE_FIELDS)
            REMAINING_CACHE.put(clazz, end + 1);
        return result;
    }
}
