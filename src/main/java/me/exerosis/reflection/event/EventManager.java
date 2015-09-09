package me.exerosis.reflection.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

public class EventManager {
    private HashMap<Class<?>, HashMap<Object, HashMap<Method, EventHandler>>> listeners = new HashMap<>();

    public synchronized void registerListener(Object listener) {
        for (Method method : listener.getClass().getMethods()) {
            EventHandler eventListener = method.getAnnotation(EventHandler.class);

            if (eventListener == null || method.getParameterTypes().length != 1)
                continue;

            Class<?> c = method.getParameterTypes()[0];

            HashMap<Object, HashMap<Method, EventHandler>> eventData = listeners.get(c);

            if (eventData == null) {
                eventData = new HashMap<>();
                listeners.put(c, eventData);
            }

            HashMap<Method, EventHandler> methods = eventData.get(listener);

            if (methods == null) {
                methods = new HashMap<>();
                eventData.put(listener, methods);
            }

            methods.put(method, eventListener);

        }

    }

    public synchronized <T> void callEvent(T event) {

        List<Entry<Object, Entry<Method, EventHandler>>> listenerMethods = getListenerMethods(event);

        fireEvent(event, listenerMethods, false);
        fireEvent(event, listenerMethods, true);
    }

    public synchronized void unregisterListener(Object listener) {
        Set<Class<?>> remove = new HashSet<>();

        for (Entry<Class<?>, HashMap<Object, HashMap<Method, EventHandler>>> entry : listeners.entrySet()) {

            entry.getValue().remove(listener);
            if (entry.getValue().isEmpty())
                remove.add(entry.getKey());
        }
        for (Class<?> clazz : remove) {
            listeners.remove(clazz);
        }
    }

    private void fireEvent(Object event, List<Entry<Object, Entry<Method, EventHandler>>> listenerMethods, boolean postEvent) {

        for (Entry<Object, Entry<Method, EventHandler>> entry : listenerMethods) {

            if (((entry.getValue().getValue().ignoreCancelled()) && ((event instanceof Cancellable)) && (((Cancellable) event).isCancelled()))
                    || postEvent != entry.getValue().getValue().postEvent()) {
                continue;
            }

            try {
                entry.getValue().getKey().invoke(entry.getKey(), event);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

                e.printStackTrace();
            }

        }

    }

    private List<Entry<Object, Entry<Method, EventHandler>>> getListenerMethods(Object event) {

        List<Entry<Object, Entry<Method, EventHandler>>> listenerMethods = new ArrayList<>();

        for (Entry<Class<?>, HashMap<Object, HashMap<Method, EventHandler>>> entry : listeners.entrySet()) {

            if (entry.getKey().isInstance(event)) {
                for (Entry<Object, HashMap<Method, EventHandler>> entry2 : entry.getValue().entrySet()) {

                    for (Entry<Method, EventHandler> entry3 : entry2.getValue().entrySet()) {
                        listenerMethods.add(new SimpleEntry<>(entry2.getKey(), entry3));
                    }
                }
            }
        }

        if (listenerMethods.isEmpty())
            return listenerMethods;

        Collections.sort(listenerMethods, (o1, o2) -> (o1.getValue().getValue().priority().getSlot() > o2.getValue().getValue().priority().getSlot()) ? 1 : -1);

        return listenerMethods;
    }


    public synchronized <T> void callEvent(T event, EventExecutor<T> executor) {

        List<Entry<Object, Entry<Method, EventHandler>>> listenerMethods = getListenerMethods(event);

        fireEvent(event, listenerMethods, false);
        if (!((event instanceof Cancellable)) || !(((Cancellable) event).isCancelled())) {
            executor.execute(event);
        }
        fireEvent(event, listenerMethods, true);
    }
}