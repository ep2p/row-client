package labs.psychogen.row.client;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;

public interface RowClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback);
    void subscribe(RowRequest<?, ?> rowRequest, SubscriptionCallback<?> callback, SubscriptionListener<?> subscriptionListener);
    void init();
    void close();
}
