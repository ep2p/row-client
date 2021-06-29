package io.ep2p.row.client.callback;

import io.ep2p.row.client.ws.WebsocketSession;
import io.ep2p.row.client.RowClient;

import javax.websocket.CloseReason;

public interface RowTransportListener<S extends WebsocketSession> {
    void onOpen(S rowWebsocketSession);
    void onError(S rowWebsocketSession, Throwable throwable);
    void onClose(RowClient rowClient, S rowWebsocketSession, CloseReason closeReason);

    class Default<S extends WebsocketSession> implements RowTransportListener<S> {
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
