package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.RowClient;
import labs.psychogen.row.client.callback.RowErrorHandler;
import labs.psychogen.row.client.pipeline.StoppablePipeline;
import labs.psychogen.row.client.tyrus.handler.MessageHandlerInput;
import labs.psychogen.row.client.ws.MessageHandler;
import labs.psychogen.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public class RowMessageHandler implements MessageHandler {
    private final StoppablePipeline<MessageHandlerInput, Void> pipeline;
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;
    private final ClosePolicy<RowClient> closePolicy;
    private final RowErrorHandler rowErrorHandler;
    private final RowClient rowClient;

    public RowMessageHandler(StoppablePipeline<MessageHandlerInput, Void> pipeline, ConnectionRepository<RowWebsocketSession> connectionRepository, ClosePolicy<RowClient> closePolicy, RowErrorHandler rowErrorHandler, RowClient rowClient) {
        this.pipeline = pipeline;
        this.connectionRepository = connectionRepository;
        this.closePolicy = closePolicy;
        this.rowErrorHandler = rowErrorHandler;
        this.rowClient = rowClient;
    }

    public void onOpen(RowWebsocketSession rowWebsocketSession) {
        connectionRepository.setConnection(rowWebsocketSession);
    }

    public void onMessage(RowWebsocketSession rowWebsocketSession, String text) {
        pipeline.execute(new MessageHandlerInput(text), null);
    }

    public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {
        rowErrorHandler.onError(rowWebsocketSession, throwable);
    }

    public void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {
        closePolicy.execute(this.rowClient);
    }
}
