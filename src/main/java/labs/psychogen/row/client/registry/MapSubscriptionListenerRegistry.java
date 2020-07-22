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
}
