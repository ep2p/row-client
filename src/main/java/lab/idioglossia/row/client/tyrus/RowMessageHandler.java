package lab.idioglossia.row.client.tyrus;

import lab.idioglossia.row.client.RowClient;
import lab.idioglossia.row.client.callback.RowTransportListener;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.tyrus.handler.MessageHandlerInput;
import lab.idioglossia.row.client.ws.MessageHandler;
import lab.idioglossia.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public class RowMessageHandler implements MessageHandler {
    private final StoppablePipeline<MessageHandlerInput, Void> pipeline;
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;
    private final RowTransportListener rowTransportListener;
    private final RowClient rowClient;

    public RowMessageHandler(StoppablePipeline<MessageHandlerInput, Void> pipeline, ConnectionRepository<RowWebsocketSession> connectionRepository, RowTransportListener rowTransportListener, RowClient rowClient) {
        this.pipeline = pipeline;
        this.connectionRepository = connectionRepository;
        this.rowTransportListener = rowTransportListener;
        this.rowClient = rowClient;
    }

    public void onOpen(RowWebsocketSession rowWebsocketSession) {
        connectionRepository.setConnection(rowWebsocketSession);
        rowTransportListener.onOpen(rowWebsocketSession);
    }

    public void onMessage(RowWebsocketSession rowWebsocketSession, String text) {
        pipeline.execute(new MessageHandlerInput(text), null);
    }

    public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {
        rowTransportListener.onError(rowWebsocketSession, throwable);
    }

    public void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {
        rowTransportListener.onClose(this.rowClient, rowWebsocketSession, closeReason);
    }
}
