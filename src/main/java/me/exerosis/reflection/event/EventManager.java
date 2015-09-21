package me.exerosis.reflection.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class EventManager {
    private HashMap<Class<?>, HashMap<Object, HashMap<Method, EventListener>>> listeners = new HashMap<>();

    public synchronized void registerListener(Object listener) {
        for (Method method : listener.getClass().getMethods()) {
            EventListener eventListener = method.getAnnotation(EventListener.class);

            if (eventListener == null || method.getParameterTypes().length != 1)
                continue;

            Class<?> c = method.getParameterTypes()[0];

            HashMap<Object, HashMap<Method, EventListener>> eventData = listeners.get(c);

            if (eventData == null) {
                eventData = new HashMap<>();
                listeners.put(c, eventData);
            }

            HashMap<Method, EventListener> methods = eventData.get(listener);

            if (methods == null) {
                methods = new HashMap<>();
                eventData.put(listener, methods);
            }

            methods.put(method, eventListener);

        }

    }

    public synchronized <T> void callEvent(T event) {

        List<Entry<Object, Entry<Method, EventListener>>> listenerMethods = getListenerMethods(event);

        fireEvent(event, listenerMethods, false);
        fireEvent(event, listenerMethods, true);
    }

    public synchronized void unregisterListener(Object listener) {
        Set<Class<?>> remove = new HashSet<>();

        for (Entry<Class<?>, HashMap<Object, HashMap<Method, EventListener>>> entry : listeners.entrySet()) {

            entry.getValue().remove(listener);
            if (entry.getValue().isEmpty())
                remove.add(entry.getKey());
        }
        remove.forEach(listeners::remove);
    }

    private void fireEvent(Object event, List<Entry<Object, Entry<Method, EventListener>>> listenerMethods, boolean postEvent) {

        for (Entry<Object, Entry<Method, EventListener>> entry : listenerMethods) {

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

    private List<Entry<Object, Entry<Method, EventListener>>> getListenerMethods(Object event) {

        List<Entry<Object, Entry<Method, EventListener>>> listenerMethods = new ArrayList<>();

        listeners.entrySet().stream().filter(entry -> entry.getKey().isInstance(event)).forEach(entry -> {
            for (Entry<Object, HashMap<Method, EventListener>> entry2 : entry.getValue().entrySet()) {
                listenerMethods.addAll(entry2.getValue().entrySet().stream().map(entry3 -> new SimpleEntry<>(entry2.getKey(), entry3)).collect(Collectors.toList()));
            }
        });

        if (listenerMethods.isEmpty())
            return listenerMethods;

        Collections.sort(listenerMethods, (o1, o2) -> (o1.getValue().getValue().priority().getSlot() > o2.getValue().getValue().priority().getSlot()) ? 1 : -1);

        return listenerMethods;
    }


    public synchronized <T> void callEvent(T event, EventExecutor<T> executor) {

        List<Entry<Object, Entry<Method, EventListener>>> listenerMethods = getListenerMethods(event);

        fireEvent(event, listenerMethods, false);
        if (!((event instanceof Cancellable)) || !(((Cancellable) event).isCancelled())) {
            executor.execute(event);
        }
        fireEvent(event, listenerMethods, true);
    }
}