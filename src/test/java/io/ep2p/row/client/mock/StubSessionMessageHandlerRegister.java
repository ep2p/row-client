package io.ep2p.row.client.mock;

import io.ep2p.row.client.ws.MessageHandler;

import javax.websocket.CloseReason;
import java.nio.ByteBuffer;

public class StubSessionMessageHandlerRegister {
    private final StubSession stubSession;

    public StubSessionMessageHandlerRegister(StubSession stubSession) {
        this.stubSession = stubSession;
    }


    public void register(StubWebsocketSession stubWebsocketSession, MessageHandler<StubWebsocketSession> messageHandler){
        stubWebsocketSession.setNativeSession(stubSession);
        stubSession.setSessionListener(new StubSession.SessionListener() {
            @Override
            public void onOpen() {
                messageHandler.onOpen(stubWebsocketSession);
            }

            @Override
            public void onClose() {
                messageHandler.onClose(stubWebsocketSession, new CloseReason(new CloseReason.CloseCode() {
                    @Override
                    public int getCode() {
                        return 0;
                    }
                }, ""));
            }

            @Override
            public void onPing(ByteBuffer byteBuffer) {

            }

            @Override
            public void onPong(ByteBuffer byteBuffer) {
                messageHandler.onPong(stubWebsocketSession, byteBuffer);
            }

            @Override
            public void onTextMessage(String message) {
                messageHandler.onMessage(stubWebsocketSession, message);
            }
        });
    }

}
