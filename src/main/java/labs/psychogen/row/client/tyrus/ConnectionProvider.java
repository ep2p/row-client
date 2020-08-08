package labs.psychogen.row.client.tyrus;

import javax.websocket.Session;

public interface ConnectionProvider {
    Session getSession();
}
