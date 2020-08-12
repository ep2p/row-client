package labs.psychogen.row.client;

import labs.psychogen.row.client.model.RowRequest;

public interface Subscription {
    void close(RowRequest<?, ?> rowRequest);
    String getId();
    String eventName();
}
