package labs.psychogen.row.client.registry;

import labs.psychogen.row.client.callback.SubscriptionListener;

public interface SubscriptionListenerRegistry {
    void registerListener(String event, SubscriptionListener<?> subscriptionListener);
    SubscriptionListener<?> getSubscriptionListener(String event);
    void unRegister(String event);
}
