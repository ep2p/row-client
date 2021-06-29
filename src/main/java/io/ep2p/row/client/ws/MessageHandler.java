package io.ep2p.row.client.ws;

import javax.websocket.CloseReason;
import java.nio.ByteBuffer;

public interface MessageHandler<S extends WebsocketSession> {
    void onOpen(S session);
    void onMessage(S session, String text);
    void onError(S session, Throwable throwable);
    void onClose(S session, CloseReason closeReason);
    default void onPong(S session, ByteBuffer payload){}
}
