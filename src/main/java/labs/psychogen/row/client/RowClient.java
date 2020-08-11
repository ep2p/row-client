package labs.psychogen.row.client;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;

import java.io.IOException;

public interface RowClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException;
    void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException;
    void open();
    void close();
}
