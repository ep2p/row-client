package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.RowClient;
import lab.idioglossia.row.client.ws.WebsocketSession;

import javax.websocket.CloseReason;

public interface RowTransportListener<S extends WebsocketSession> {
    void onOpen(S rowWebsocketSession);
    void onError(S rowWebsocketSession, Throwable throwable);
    void onClose(RowClient rowClient, S rowWebsocketSession, CloseReason closeReason);

    class Default implements RowTransportListener {
        @Override
        public void onOpen(WebsocketSession rowWebsocketSession) {

        }

        @Override
        public void onError(WebsocketSession rowWebsocketSession, Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onClose(RowClient rowClient, WebsocketSession rowWebsocketSession, CloseReason closeReason) {

        }
    }
}
