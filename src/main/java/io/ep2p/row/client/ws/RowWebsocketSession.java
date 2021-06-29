package io.ep2p.row.client.ws;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

public class RowWebsocketSession extends AbstractWebsocketSession<Session> {
    private final URI uri;
    private final WebsocketConfig websocketConfig;

    public RowWebsocketSession(Map<String, Object> attributes, URI uri, WebsocketConfig websocketConfig) {
        super(attributes);
        this.uri = uri;
        this.websocketConfig = websocketConfig;
    }

    public URI getUri() {
        return uri;
    }

    public WebsocketConfig getWebsocketConfig() {
        return websocketConfig;
    }

    public void close() throws IOException {
        Session nativeSession = getNativeSession();
        assert nativeSession != null;
        nativeSession.close();
    }

    public void close(CloseReason closeReason) throws IOException {
        Session nativeSession = getNativeSession();
        assert nativeSession != null;
        nativeSession.close(closeReason);
    }

    public boolean isOpen(){
        Session nativeSession = getNativeSession();
        return nativeSession != null && nativeSession.isOpen();
    }

    public boolean isSecure(){
        Session nativeSession = getNativeSession();
        return nativeSession != null && nativeSession.isSecure();
    }

    public void sendTextMessage(String payload) throws IOException {
        ((Session)this.getNativeSession()).getBasicRemote().sendText(payload, true);
    }

    public void sendPingMessage(ByteBuffer byteBuffer) throws IOException {
        ((Session)this.getNativeSession()).getBasicRemote().sendPing(byteBuffer == null ? ByteBuffer.allocate(0) : byteBuffer);
    }

    public void sendPongMessage(ByteBuffer byteBuffer) throws IOException {
        ((Session)this.getNativeSession()).getBasicRemote().sendPong(byteBuffer == null ? ByteBuffer.allocate(0) : byteBuffer);
    }

    public void closeInternal(CloseReason closeReason) throws IOException {
        ((Session)this.getNativeSession()).close(closeReason);
    }
}
