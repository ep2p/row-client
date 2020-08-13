package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.pipeline.StoppablePipeline;
import labs.psychogen.row.client.tyrus.handler.MessageHandlerInput;
import labs.psychogen.row.client.ws.MessageHandler;
import labs.psychogen.row.client.ws.RowWebsocketSession;

import javax.websocket.CloseReason;

public class RowMessageHandler implements MessageHandler {
    private final StoppablePipeline<MessageHandlerInput, Void> pipeline;
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;

    public RowMessageHandler(StoppablePipeline<MessageHandlerInput, Void> pipeline, ConnectionRepository<RowWebsocketSession> connectionRepository) {
        this.pipeline = pipeline;
        this.connectionRepository = connectionRepository;
    }

    public void onOpen(RowWebsocketSession rowWebsocketSession) {
        connectionRepository.setConnection(rowWebsocketSession);
    }

    public void onMessage(RowWebsocketSession rowWebsocketSession, String text) {
        pipeline.execute(new MessageHandlerInput(text), null);
    }

    public void onError(RowWebsocketSession rowWebsocketSession, Throwable throwable) {

    }

    public void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {
    }
}
