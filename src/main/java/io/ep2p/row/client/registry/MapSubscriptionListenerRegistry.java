package io.ep2p.row.client.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapSubscriptionListenerRegistry implements SubscriptionListenerRegistry {
    private final Map<String, SubscriptionRegistryModel<?>> map = new ConcurrentHashMap<String, SubscriptionRegistryModel<?>>();

    public void registerListener(String event, SubscriptionRegistryModel<?> subscriptionRegistryModel){
        map.put(event, subscriptionRegistryModel);
    }

    public SubscriptionRegistryModel<?> getSubscriptionListener(String event){
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
