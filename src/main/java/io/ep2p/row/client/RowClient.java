package io.ep2p.row.client;

import io.ep2p.row.client.callback.ResponseCallback;
import io.ep2p.row.client.callback.SubscriptionListener;
import io.ep2p.row.client.model.RowRequest;

import java.io.IOException;

public interface RowClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException;
    void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException;
    void open();
    void close();
}
