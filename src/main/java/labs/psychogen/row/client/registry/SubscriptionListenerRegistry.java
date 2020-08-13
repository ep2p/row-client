package labs.psychogen.row.client.registry;

import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.callback.SubscriptionListener;
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
