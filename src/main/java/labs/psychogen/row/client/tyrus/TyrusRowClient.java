package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.RowClient;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;

import javax.annotation.PostConstruct;
import javax.websocket.Session;

public class TyrusRowClient implements RowClient, ConnectionProvider {
    private final RequestSender requestSender;

    public TyrusRowClient(CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry) {
        this.requestSender = new RequestSender(this, new UUIDMessageIdGenerator(), callbackRegistry, subscriptionListenerRegistry);
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) {
        requestSender.sendRequest(rowRequest, callback);
    }

    public void subscribe(RowRequest<?, ?> rowRequest, SubscriptionCallback<?> callback, SubscriptionListener<?> subscriptionListener) {
        requestSender.sendSubscribe(rowRequest, callback,subscriptionListener);
    }

    @PostConstruct
    public void init() {

    }

    public void close() {

    }

    public Session getSession() {
        return null;
    }
}
