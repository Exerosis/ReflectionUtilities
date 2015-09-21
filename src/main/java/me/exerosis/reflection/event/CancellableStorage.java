package me.exerosis.reflection.event;

import java.util.Map;
import java.util.WeakHashMap;

public class CancellableStorage {
    private static Map<Cancellable, PseudoInstance> instances = new WeakHashMap<>();

    private CancellableStorage() {
    }

    public static boolean isCancelled(Cancellable instance) {
        return getInstance(instance).isCancelled();
    }

    public static void setCancelled(Cancellable instance, boolean cancelled) {
        getInstance(instance).setCancelled(cancelled);
    }

    @SuppressWarnings("unchecked")
    private static PseudoInstance getInstance(Cancellable instance) {
        PseudoInstance pseudoInstance = instances.get(instance);
        if (pseudoInstance != null)
            return pseudoInstance;
        pseudoInstance = new PseudoInstance();
        instances.put(instance, pseudoInstance);
        return pseudoInstance;
    }

    public static class PseudoInstance implements Cancellable {
        private boolean cancelled;

        public PseudoInstance() {
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
}