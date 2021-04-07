package lab.idioglossia.row.client.mock;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

public class MockSession {
    private volatile boolean open;
    @Getter
    @Setter
    private volatile SessionListener sessionListener;
    private final MockServer mockServer;

    public MockSession(MockServer mockServer) {
        this.mockServer = mockServer;
        this.setSessionListener(new SessionListener(){});
    }

    public void open(){
        this.open = true;
        this.mockServer.onOpen(this);
    }

    boolean isOpen(){
        return open;
    }

    public void close(){
        this.open = false;
        this.mockServer.onClose(this);
    }

    public void sendPingMessage(ByteBuffer byteBuffer){
        this.mockServer.onPing(this, byteBuffer);
    }

    public void sendPongMessage(ByteBuffer byteBuffer){
        this.mockServer.onPong(this, byteBuffer);
    }

    public void sendTextMessage(String text){
        this.mockServer.onMessage(this, text);
    }

    public interface SessionListener {
        default void onOpen(){}
        default void onClose(){}
        default void onTextMessage(String message){}
        default void onPing(ByteBuffer byteBuffer){}
        default void onPong(ByteBuffer byteBuffer){}
    }

    public interface MockServer {
        void onOpen(MockSession mockSession);
        void onClose(MockSession mockSession);
        void onPing(MockSession mockSession, ByteBuffer byteBuffer);
        void onPong(MockSession mockSession, ByteBuffer byteBuffer);
        void onMessage(MockSession mockSession, String message);
    }
}
