package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.ws.RowWebsocketSession;

public interface ConnectionProvider {
    RowWebsocketSession getSession();
}
