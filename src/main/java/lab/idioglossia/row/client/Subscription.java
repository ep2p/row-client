package lab.idioglossia.row.client;

import lab.idioglossia.row.client.model.RowRequest;

public interface Subscription {
    void close(RowRequest<?, ?> rowRequest);
    void close();
    String getId();
    String eventName();
}
