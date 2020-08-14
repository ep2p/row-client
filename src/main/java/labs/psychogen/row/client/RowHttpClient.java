package labs.psychogen.row.client;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.model.RowRequest;

public interface RowHttpClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback);
}
