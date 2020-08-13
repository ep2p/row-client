package labs.psychogen.row.client.tyrus.handler;

import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.exception.ResponseException;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.model.RowResponse;
import labs.psychogen.row.client.model.protocol.Naming;
import labs.psychogen.row.client.model.protocol.ResponseDto;
import labs.psychogen.row.client.model.protocol.RowResponseStatus;
import labs.psychogen.row.client.pipeline.StoppablePipeline;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.tyrus.ConnectionRepository;
import labs.psychogen.row.client.util.MessageConverter;
import labs.psychogen.row.client.ws.RowWebsocketSession;
import lombok.SneakyThrows;

import java.util.Random;

public class CallbackCallerHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final CallbackRegistry callbackRegistry;
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;
    private final MessageConverter messageConverter;

    public CallbackCallerHandler(CallbackRegistry callbackRegistry, ConnectionRepository<RowWebsocketSession> connectionRepository) {
        this.callbackRegistry = callbackRegistry;
        this.connectionRepository = connectionRepository;
        messageConverter = new MessageConverter();
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) {
        ResponseDto responseDto = input.getResponseDto();
        if (responseDto.getRequestId() == null) {
            return true;
        }
        callCallback(input);
        return false;
    }

    private void callCallback(MessageHandlerInput input) {
        ResponseCallback<?> callback = callbackRegistry.getCallback(input.getResponseDto().getRequestId());
        try {
            RowResponse rowResponse = getRowResponse(input);
            rowResponse.setSubscription(getSubscription(input.getResponseDto()));
            callback.onResponse(rowResponse);
        } catch (ResponseException e) {
            callback.onError(e);
        }

    }

    private RowResponse getRowResponse(MessageHandlerInput input) throws ResponseException {
        ResponseDto responseDto = input.getResponseDto();
        if (responseDto.getStatus() == RowResponseStatus.OK.getId()) {
            RowResponse rowResponse = new RowResponse();
            rowResponse.setBody(responseDto.getBody());
            rowResponse.setHeaders(responseDto.getHeaders());
            rowResponse.setRequestId(responseDto.getRequestId());
            rowResponse.setStatus(RowResponseStatus.OK);
            return rowResponse;
        }
        throw new ResponseException(responseDto.getStatus(), input.getJson());
    }

    private Subscription getSubscription(ResponseDto responseDto){
        String subscriptionEventName = responseDto.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME);
        String subscriptionId = responseDto.getHeaders().get(Naming.SUBSCRIPTION_Id_HEADER_NAME);
        if(subscriptionEventName != null && subscriptionId != null)
            return new RowSubscription(subscriptionId, subscriptionEventName);

        return null;
    }

    private class RowSubscription implements Subscription {
        private final String subscriptionId;
        private final String subscriptionEventName;

        private RowSubscription(String subscriptionId, String subscriptionEventName) {
            this.subscriptionId = subscriptionId;
            this.subscriptionEventName = subscriptionEventName;
        }

        @Override
        @SneakyThrows
        public void close(RowRequest<?, ?> rowRequest) {
            connectionRepository.getConnection().sendTextMessage(messageConverter.getJson(String.valueOf(new Random().nextInt(100)), rowRequest));
        }

        @Override
        public String getId() {
            return this.subscriptionId;
        }

        @Override
        public String eventName() {
            return this.subscriptionEventName;
        }

        @Override
        public String toString() {
            return "RowSubscription{" +
                    "subscriptionId='" + subscriptionId + '\'' +
                    ", subscriptionEventName='" + subscriptionEventName + '\'' +
                    '}';
        }
    }
}
