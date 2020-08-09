package labs.psychogen.row.client.registry;

import labs.psychogen.row.client.callback.SubscriptionListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapSubscriptionListenerRegistry implements SubscriptionListenerRegistry {
    private final Map<String, SubscriptionListener<?>> map = new ConcurrentHashMap<String, SubscriptionListener<?>>();

    public void registerListener(String event, SubscriptionListener<?> subscriptionListener){
        map.put(event, subscriptionListener);
    }

    public SubscriptionListener<?> getSubscriptionListener(String event){
        return map.get(event);
    }

    public void unRegister(String event){
        map.remove(event);
    }

    public static class Factory {
        private static MapSubscriptionListenerRegistry mapSubscriptionListenerRegistry;

        private Factory(){}

        public static synchronized MapSubscriptionListenerRegistry getInstance(){
            if(mapSubscriptionListenerRegistry == null){
                mapSubscriptionListenerRegistry = new MapSubscriptionListenerRegistry();
            }
            return mapSubscriptionListenerRegistry;
        }
    }
}
