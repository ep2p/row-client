package labs.psychogen.row.client.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.*;
import java.nio.ByteBuffer;

public class RowWebsocketHandlerAdapter extends Endpoint {
    private final Log logger = LogFactory.getLog(RowWebsocketHandlerAdapter.class);
    private final RowWebsocketSession rowWebsocketSession;
    private final MessageHandler messageHandler;

    public RowWebsocketHandlerAdapter(RowWebsocketSession rowWebsocketSession, MessageHandler messageHandler) {
        this.rowWebsocketSession = rowWebsocketSession;
        this.messageHandler = messageHandler;
    }

    public void onOpen(final Session session, EndpointConfig endpointConfig) {
        rowWebsocketSession.setNativeSession(session);
        session.addMessageHandler(new javax.websocket.MessageHandler.Partial<String>() {
            public void onMessage(String message, boolean isLast) {
                RowWebsocketHandlerAdapter.this.handleTextMessage(session, message, isLast);
            }
        });
        session.addMessageHandler(new javax.websocket.MessageHandler.Whole<PongMessage>() {
            public void onMessage(PongMessage message) {
                RowWebsocketHandlerAdapter.this.handlePongMessage(session, message.getApplicationData());
            }
        });
        messageHandler.onOpen(rowWebsocketSession);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        messageHandler.onClose(this.rowWebsocketSession, closeReason);
    }

    @Override
    public void onError(Session session, Throwable thr) {
        messageHandler.onError(this.rowWebsocketSession, thr);
    }

    private void handleTextMessage(Session session, String payload, boolean isLast) {
        messageHandler.onMessage(this.rowWebsocketSession, payload);
    }

    private void handlePongMessage(Session session, ByteBuffer payload) {
        messageHandler.onPong(this.rowWebsocketSession, payload);
    }

    private static void tryCloseWithError(WebSocketSession session, Throwable exception, Log logger) {
        if (logger.isErrorEnabled()) {
            logger.error("Closing session due to exception for " + session, exception);
        }

        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
            } catch (Throwable var4) {
            }
        }

    }
}
