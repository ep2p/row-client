package io.ep2p.row.client.mock;

import io.ep2p.row.client.ws.AbstractWebsocketSession;

import javax.websocket.CloseReason;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class StubWebsocketSession extends AbstractWebsocketSession<StubSession> {

    public StubWebsocketSession() {
        super(new HashMap<>());
    }

    public StubWebsocketSession(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public void close(CloseReason closeReason) throws Exception {
        getNativeSession().close();
    }

    @Override
    public boolean isOpen() {
        return getNativeSession().isOpen();
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public void sendTextMessage(String payload) throws Exception {
        getNativeSession().sendTextMessage(payload);
    }

    @Override
    public void sendPingMessage(ByteBuffer byteBuffer) throws Exception {
        getNativeSession().sendPingMessage(byteBuffer);
    }

    @Override
    public void sendPongMessage(ByteBuffer byteBuffer) throws Exception {
        getNativeSession().sendPongMessage(byteBuffer);
    }

    @Override
    public void closeInternal(CloseReason closeReason) throws Exception {
        getNativeSession().close();
    }
}
