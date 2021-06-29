package io.ep2p.row.client;

import io.ep2p.row.client.callback.ResponseCallback;
import io.ep2p.row.client.model.RowRequest;

public interface RowHttpClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback);
}
