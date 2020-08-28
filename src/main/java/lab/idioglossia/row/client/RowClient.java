package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.RowRequest;

import java.io.IOException;

public interface RowClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException;
    void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException;
    void open();
    void close();
}
