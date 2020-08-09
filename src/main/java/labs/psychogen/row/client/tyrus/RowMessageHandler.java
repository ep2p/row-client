package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import labs.psychogen.row.client.ws.MessageHandler;
import labs.psychogen.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public class RowMessageHandler implements MessageHandler {
    private final Listener listener;
    private final CallbackRegistry callbackRegistry;
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;

    public RowMessageHandler(Listener listener, CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry) {
        this.callbackRegistry = callbackRegistry;
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        assert listener != null;
        this.listener = listener;
    }

    public void onOpen(RowWebsocketSession rowWebsocketSession) {
        listener.onOpen(rowWebsocketSession);
    }

    public void onMessage(RowWebsocketSession rowWebsocketSession, String text) {

    }

    public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {

    }

    public void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {
        listener.onClose(rowWebsocketSession, closeReason);
    }

    public interface Listener {
        void onOpen(RowWebsocketSession rowWebsocketSession);
        void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason);
    }
}
