package labs.psychogen.row.client;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;

import java.io.IOException;

public abstract class RowClientDecorator implements RowClient {
    private final RowClient rowClient;

    public RowClientDecorator(RowClient rowClient) {
        this.rowClient = rowClient;
    }

    @Override
    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        rowClient.sendRequest(rowRequest, callback);
    }

    @Override
    public void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException {
        rowClient.subscribe(rowRequest, callback, subscriptionListener);
    }

    @Override
    public void open() {
        rowClient.open();
    }

    @Override
    public void close() {
        rowClient.close();
    }
}
