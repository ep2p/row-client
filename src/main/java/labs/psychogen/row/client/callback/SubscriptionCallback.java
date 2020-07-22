package labs.psychogen.row.client.callback;

public interface SubscriptionCallback<E> extends ResponseCallback<E> {
    Subscription getSubscription();
}
