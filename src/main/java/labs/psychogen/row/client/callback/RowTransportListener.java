package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.RowClient;
import labs.psychogen.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public interface RowTransportListener {
    void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable);
    void onClose(RowClient rowClient, RowWebsocketSession rowWebsocketSession, CloseReason closeReason);

    class Default implements RowTransportListener {

        @Override
        public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onClose(RowClient rowClient, RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {

        }
    }
}
