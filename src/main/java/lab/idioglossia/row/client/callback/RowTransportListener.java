package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.RowClient;
import lab.idioglossia.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public interface RowTransportListener {
    void onOpen(RowWebsocketSession rowWebsocketSession);
    void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable);
    void onClose(RowClient rowClient, RowWebsocketSession rowWebsocketSession, CloseReason closeReason);

    class Default implements RowTransportListener {

        @Override
        public void onOpen(RowWebsocketSession rowWebsocketSession) {

        }

        @Override
        public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onClose(RowClient rowClient, RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {

        }
    }
}
