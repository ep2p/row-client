package lab.idioglossia.row.client.mock;

import lab.idioglossia.row.client.ws.MessageHandler;

import javax.websocket.CloseReason;
import java.nio.ByteBuffer;

public class MockSessionMessageHandlerRegister {
    private final MockSession mockSession;

    public MockSessionMessageHandlerRegister(MockSession mockSession) {
        this.mockSession = mockSession;
    }


    public void register(MockWebsocketSession mockWebsocketSession, MessageHandler<MockWebsocketSession> messageHandler){
        mockWebsocketSession.setNativeSession(mockSession);
        mockSession.setSessionListener(new MockSession.SessionListener() {
            @Override
            public void onOpen() {
                messageHandler.onOpen(mockWebsocketSession);
            }

            @Override
            public void onClose() {
                messageHandler.onClose(mockWebsocketSession, new CloseReason(new CloseReason.CloseCode() {
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
                messageHandler.onPong(mockWebsocketSession, byteBuffer);
            }

            @Override
            public void onTextMessage(String message) {
                messageHandler.onMessage(mockWebsocketSession, message);
            }
        });
    }

}
