package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import labs.psychogen.row.client.ws.MessageHandler;
import labs.psychogen.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public class RowMessageHandler implements MessageHandler {
    private final CallbackRegistry callbackRegistry;
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;

    public RowMessageHandler(CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry, ConnectionRepository<RowWebsocketSession> connectionRepository) {
        this.callbackRegistry = callbackRegistry;
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.connectionRepository = connectionRepository;
    }

    public void onOpen(RowWebsocketSession rowWebsocketSession) {
        connectionRepository.setConnection(rowWebsocketSession);
    }

    public void onMessage(RowWebsocketSession rowWebsocketSession, String text) {

    }

    public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {

    }

    public void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {
    }
}
