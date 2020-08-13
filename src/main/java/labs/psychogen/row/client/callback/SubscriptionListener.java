package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.Subscription;

public interface SubscriptionListener<E> {
    <E> void onMessage(Subscription subscription, E e);
}
