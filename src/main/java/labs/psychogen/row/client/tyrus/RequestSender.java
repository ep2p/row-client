package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.MessageIdGenerator;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import labs.psychogen.row.client.util.MessageConverter;
import labs.psychogen.row.client.ws.RowWebsocketSession;

import java.io.IOException;

public class RequestSender {
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;
    private final MessageIdGenerator messageIdGenerator;
    private final CallbackRegistry callbackRegistry;
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final MessageConverter messageConverter;

    public RequestSender(ConnectionRepository<RowWebsocketSession> connectionRepository, MessageIdGenerator messageIdGenerator, CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry) {
        this.connectionRepository = connectionRepository;
        this.messageIdGenerator = messageIdGenerator;
        this.callbackRegistry = callbackRegistry;
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.messageConverter = new MessageConverter();
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        sendMessage(rowRequest, callback);
    }

    public <E> void sendSubscribe(RowRequest<?, ?> rowRequest, final ResponseCallback<E> callback, final SubscriptionListener<?> subscriptionListener) throws IOException {
        sendMessage(rowRequest, new RegistryResponseCallbackDecorator<E>(callback, subscriptionListenerRegistry, subscriptionListener));
    }

    private void sendMessage(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        String messageId = messageIdGenerator.generate();
        String json = messageConverter.getJson(messageId, rowRequest);
        callbackRegistry.registerCallback(messageId, callback);
        try {
            connectionRepository.getConnection().sendTextMessage(json);
        }catch (RuntimeException | IOException e){
            callbackRegistry.unregisterCallback(messageId);
            throw e;
        }
    }

}
