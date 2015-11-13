package me.exerosis.reflection;

import me.exerosis.reflection.exceptions.notfound.ReflectClassNotFoundException;

import java.util.HashMap;
import java.util.Map;

public final class Reflect {
    private static Map<String, Class<?>> classLookup = new HashMap<>();
    private static String bukkitClassPrefix = "";
    private static String nmsClassPrefix = "";

    static {
        try {
            ReflectClass<?> bukkitClass = Class("org.bukkit.Bukkit");
            bukkitClassPrefix = bukkitClass.getField("server").getValue().getClass().getPackage().getName();
            nmsClassPrefix = bukkitClassPrefix.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        } catch (Exception e) {
            System.err.print("[Reflection] No Bukkit class found, assumed running outside of Bukkit context!");
        }
    }

    // Class creators.
    public static ReflectClass<Object> Class(String className) {
        className = className.replace("{cb}", bukkitClassPrefix).replace("{nms}", nmsClassPrefix);

        Class<?> clazz = classLookup.get(className);
        if (clazz != null)
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException ignored) {
                throw new ReflectClassNotFoundException(className);
            }
        return new ReflectClass<>(clazz);
    }

    public static <T> ReflectClass<T> Class(T instance) {
        return new ReflectClass<>(instance);
    }

    public static <T> ReflectClass<T> Class(Class<T> clazz) {
        return new ReflectClass<>(clazz);
    }


    // Field Creators
    //~Instance ~Type ~Position
    public static <K, T> ReflectField<K> Field(T instance, ReflectClass<K> type, int pos) {
        return new ReflectClass<>(instance).getField(type, pos);
    }

    public static <K, T> ReflectField<K> Field(T instance, Class<K> type, int pos) {
        return Field(instance, new ReflectClass<>(type), pos);
    }

    //~Class ~Type ~Position
    public static <K, T> ReflectField<K> Field(Class<T> clazz, ReflectClass<K> type, int pos) {
        return new ReflectClass<>(clazz).getField(type, pos);
    }

    public static <K, T> ReflectField<K> Field(Class<T> clazz, Class<K> type, int pos) {
        return Field(clazz, new ReflectClass<>(type), pos);
    }


    //~Instance ~Type ~Name
    public static <K, T> ReflectField<K> Field(T instance, Class<K> type, String name) {
        return Field(instance, new ReflectClass<>(type), name);
    }

    public static <K, T> ReflectField<K> Field(T instance, ReflectClass<K> type, String name) {
        return new ReflectClass<>(instance).getField(type, name);
    }

    //~Class ~Type ~Name
    public static <K, T> ReflectField<K> Field(Class<T> clazz, Class<K> type, String name) {
        return Field(clazz, new ReflectClass<>(type), name);
    }

    public static <K, T> ReflectField<K> Field(Class<T> clazz, ReflectClass<K> type, String name) {
        return new ReflectClass<>(clazz).getField(type, name);
    }


    //~Instance ~Type
    public static <K, T> ReflectField<K> Field(T instance, Class<K> type) {
        return Field(instance, type, 0);
    }

    public static <K, T> ReflectField<K> Field(T instance, ReflectClass<K> type) {
        return Field(instance, type, 0);
    }

    //~Class ~Type
    public static <K, T> ReflectField<K> Field(Class<T> clazz, Class<K> type) {
        return Field(clazz, type, 0);
    }

    public static <K, T> ReflectField<K> Field(Class<T> clazz, ReflectClass<K> type) {
        return Field(clazz, type, 0);
    }


    //~Instance ~Name
    public static <T> ReflectField<?> Field(T instance, String name) {
        return new ReflectClass<>(instance).getField(name);
    }

    //~Class ~Name
    public static <T> ReflectField<?> Field(Class<T> clazz, String name) {
        return new ReflectClass<>(clazz).getField(name);
    }

    //Prefix Getters
    public static String getBukkitClassPrefix() {
        return bukkitClassPrefix;
    }

    public static String getNmsClassPrefix() {
        return nmsClassPrefix;
    }
}