package lab.idioglossia.row.client.ws;

import javax.websocket.CloseReason;
import java.net.URI;
import java.nio.ByteBuffer;

public interface WebsocketSession {
    URI getUri();
    void close(CloseReason closeReason) throws Exception;
    boolean isOpen();
    boolean isSecure();
    void sendTextMessage(String payload) throws Exception;
    void sendPingMessage(ByteBuffer byteBuffer) throws Exception;
    void sendPongMessage(ByteBuffer byteBuffer) throws Exception;
    void closeInternal(CloseReason closeReason) throws Exception;
}
