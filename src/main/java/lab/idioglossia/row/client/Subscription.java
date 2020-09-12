package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.model.RowRequest;

public interface Subscription {
    void close(RowRequest<?, ?> rowRequest, ResponseCallback<?> responseCallback);
    void close(ResponseCallback<?> responseCallback);
    String getId();
    String eventName();
}
