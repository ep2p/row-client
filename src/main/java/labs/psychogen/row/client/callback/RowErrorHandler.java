package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.ws.RowWebsocketSession;

public interface RowErrorHandler {
    void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable);

    class Default implements RowErrorHandler{

        @Override
        public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
