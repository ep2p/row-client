package labs.psychogen.row.client.ws;

import javax.websocket.CloseReason;
import java.nio.ByteBuffer;

public interface MessageHandler {
    void onOpen(RowWebsocketSession rowWebsocketSession);
    void onMessage(RowWebsocketSession rowWebsocketSession, String text);
    void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable);
    void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason);
    default void onPong(RowWebsocketSession rowWebsocketSession, ByteBuffer payload){}
}
