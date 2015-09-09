package me.exerosis.reflection.event;

public interface EventExecutor<T> {
    void execute(T event);
}