package me.exerosis.reflection.alpha;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static me.exerosis.reflection.FieldUtilities.*;

@SuppressWarnings("unchecked")
public interface ReflectField<FieldType, ClassType> {
    Field toField();

    default <CastType> CastType cast(ClassType instance) {
        return (CastType) get(instance);
    }

    default FieldType get(ClassType instance) {
        try {
            return (FieldType) toField().get(instance);
        } catch (Exception ignored) {
            return null;
        }
    }

    default void set(ClassType instance, FieldType value) {
        try {
            if (Modifier.isFinal(toField().getModifiers())) {
                FIELD_MODIFIERS.setInt(toField(), toField().getModifiers() & ~Modifier.FINAL);
                Field currentField = toField();
                do {
                    FIELD_ACCESSOR.set(currentField, null);
                    FIELD_ACCESSOR_OVERRIDE.set(currentField, null);
                } while ((currentField = (Field) FIELD_ROOT.get(currentField)) != null);
            }
            toField().set(instance, value);
        } catch (Exception ignored) {
        }
    }

    ReflectClass<FieldType> access(ClassType instance);
}