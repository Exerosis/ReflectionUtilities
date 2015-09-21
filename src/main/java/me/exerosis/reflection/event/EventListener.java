package me.exerosis.reflection.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EventListener {
    Priority priority() default Priority.NORMAL;

    boolean postEvent() default false;

    boolean ignoreCancelled() default true;
}