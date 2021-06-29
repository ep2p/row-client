package io.ep2p.row.client;

import io.ep2p.row.client.callback.ResponseCallback;
import io.ep2p.row.client.model.RowRequest;

public interface Subscription {
    void close(RowRequest<?, ?> rowRequest, ResponseCallback<?> responseCallback);
    void close(ResponseCallback<?> responseCallback);
    String getId();
    String eventName();
}
