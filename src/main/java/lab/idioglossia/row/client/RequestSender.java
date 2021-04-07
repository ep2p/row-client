package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.registry.CallbackRegistry;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;
import lab.idioglossia.row.client.util.MessageConverter;
import lab.idioglossia.row.client.ws.WebsocketSession;

import java.io.IOException;

public class RequestSender {
    private final ConnectionRepository<? extends WebsocketSession> connectionRepository;
    private final MessageIdGenerator messageIdGenerator;
    private final CallbackRegistry callbackRegistry;
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final MessageConverter messageConverter;
    private final ResponseCallback.API responseCallbackApi = new ResponseCallback.API();

    public RequestSender(ConnectionRepository<? extends WebsocketSession> connectionRepository, MessageIdGenerator messageIdGenerator, CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry, MessageConverter messageConverter) {
        this.connectionRepository = connectionRepository;
        this.messageIdGenerator = messageIdGenerator;
        this.callbackRegistry = callbackRegistry;
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.messageConverter = messageConverter;
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        responseCallbackApi.setRequest(callback, rowRequest);
        sendMessage(rowRequest, callback);
    }

    public <E> void sendSubscribe(RowRequest<?, ?> rowRequest, final ResponseCallback<E> callback, final SubscriptionListener<?> subscriptionListener) throws IOException {
        responseCallbackApi.setRequest(callback, rowRequest);
        sendMessage(rowRequest, new RegistryResponseCallbackDecorator<E>(callback, subscriptionListenerRegistry, subscriptionListener));
    }

    private void sendMessage(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        String messageId = messageIdGenerator.generate();
        String json = messageConverter.getJson(messageId, rowRequest);
        callbackRegistry.registerCallback(messageId, callback);
        try {
            connectionRepository.getConnection().sendTextMessage(json);
        }catch (Exception e){
            callbackRegistry.unregisterCallback(messageId);
            throw new IOException(e);
        }
    }

}
