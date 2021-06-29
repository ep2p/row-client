package io.ep2p.row.client.registry;

import io.ep2p.row.client.callback.SubscriptionListener;
import io.ep2p.row.client.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface SubscriptionListenerRegistry {
    void registerListener(String event, SubscriptionRegistryModel<?> subscriptionRegistryModel);
    SubscriptionRegistryModel<?> getSubscriptionListener(String event);
    void unRegister(String event);

    @Getter
    @Setter
    @AllArgsConstructor
    class SubscriptionRegistryModel<E> {
        private Subscription subscription;
        private SubscriptionListener<E> subscriptionListener;
    }
}
