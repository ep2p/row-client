package lab.idioglossia.row.client.registry;

import lab.idioglossia.row.client.Subscription;
import lab.idioglossia.row.client.callback.SubscriptionListener;
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
