package labs.psychogen.row.client.tyrus;

import org.springframework.web.socket.WebSocketSession;

public interface ConnectionProvider {
    WebSocketSession getSession();
}
