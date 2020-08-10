package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.model.RowResponse;

public class SubscriptionCallbackDecorator<E> implements SubscriptionCallback<E> {
    private final SubscriptionCallback<E> subscriptionCallback;

    public SubscriptionCallbackDecorator(SubscriptionCallback<E> subscriptionCallback) {
        this.subscriptionCallback = subscriptionCallback;
    }

    @Override
    public Subscription getSubscription() {
        return subscriptionCallback.getSubscription();
    }

    @Override
    public void onResponse(RowResponse<E> rowResponse) {
        subscriptionCallback.onResponse(rowResponse);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriptionCallback.onError(throwable);
    }
}
