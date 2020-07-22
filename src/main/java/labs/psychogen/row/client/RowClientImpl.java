package labs.psychogen.row.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;

import java.util.List;

public class RowClientImpl implements RowClient {
    private final ObjectMapper objectMapper;

    public RowClientImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) {

    }

    public void subscribe(RowRequest<?, ?> rowRequest, SubscriptionCallback<?> callback, SubscriptionListener<?> subscriptionListener) {

    }

    public List<Subscription> getSubscriptions() {
        return null;
    }

    public Subscription getSubscription(String eventName) {
        return null;
    }

    public void close() {

    }
}
