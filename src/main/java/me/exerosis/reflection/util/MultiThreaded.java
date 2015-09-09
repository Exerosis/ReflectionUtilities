package me.exerosis.reflection.util;

public interface MultiThreaded {
    default void runNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
