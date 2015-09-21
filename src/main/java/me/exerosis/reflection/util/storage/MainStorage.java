package me.exerosis.reflection.util.storage;

import java.util.Map;
import java.util.WeakHashMap;

public class MainStorage {
    public static Map<Class<?>, InterfaceStorage> interfaces = new WeakHashMap<>();

    public PseudoInstance get(Object instance) {
        InterfaceStorage storage = interfaces.get(instance.getClass());
        if (storage != null)
            return storage.get(instance);
        storage = new InterfaceStorage();
        interfaces.put(instance.getClass(), storage);
        return storage.get(instance);
    }

    public class InterfaceStorage {
        private Map<Object, PseudoInstance> instances = new WeakHashMap<>();

        public PseudoInstance get(Object instance) {
            PseudoInstance pseudoInstance = instances.get(instance);
            if (pseudoInstance != null)
                return pseudoInstance;
            pseudoInstance = new PseudoInstance();
            instances.put(instance, pseudoInstance);
            return pseudoInstance;
        }
    }
}