package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.model.RowResponse;

public class SubscriptionCallbackDecorator<E> implements ResponseCallback<E> {
    private final ResponseCallback<E> subscriptionCallback;

    public SubscriptionCallbackDecorator(ResponseCallback<E> responseCallback) {
        this.subscriptionCallback = responseCallback;
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
