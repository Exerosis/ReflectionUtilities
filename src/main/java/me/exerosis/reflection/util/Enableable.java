package me.exerosis.reflection.util;

/**
 * Created by The Exerosis on 8/14/2015.
 */
public interface Enableable {
    void onEnable();

    boolean isEnabled();

    void onDisable();
}
