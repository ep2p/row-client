package labs.psychogen.row.client.callback;
import labs.psychogen.row.client.Subscription;

public interface SubscriptionCallback<E> extends ResponseCallback<E> {
    Subscription getSubscription();
}
