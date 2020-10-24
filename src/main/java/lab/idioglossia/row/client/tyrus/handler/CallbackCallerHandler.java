package lab.idioglossia.row.client.tyrus.handler;

import lab.idioglossia.row.client.Subscription;
import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.exception.MessageDataProcessingException;
import lab.idioglossia.row.client.exception.ResponseException;
import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.RowResponse;
import lab.idioglossia.row.client.model.protocol.Naming;
import lab.idioglossia.row.client.model.protocol.ResponseDto;
import lab.idioglossia.row.client.model.protocol.RowResponseStatus;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.registry.CallbackRegistry;
import lab.idioglossia.row.client.tyrus.ConnectionRepository;
import lab.idioglossia.row.client.util.MessageConverter;
import lab.idioglossia.row.client.ws.RowWebsocketSession;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Random;

public class CallbackCallerHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final CallbackRegistry callbackRegistry;
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;
    private final MessageConverter messageConverter;
    private final ResponseCallback.API responseCallbackApi = new ResponseCallback.API();

    public CallbackCallerHandler(CallbackRegistry callbackRegistry, ConnectionRepository<RowWebsocketSession> connectionRepository, MessageConverter messageConverter) {
        this.callbackRegistry = callbackRegistry;
        this.connectionRepository = connectionRepository;
        this.messageConverter = messageConverter;
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) throws MessageDataProcessingException {
        ResponseDto responseDto = input.getResponseDto();
        if (responseDto.getRequestId() == null) {
            return true;
        }
        callCallback(input);
        return false;
    }

    private void callCallback(MessageHandlerInput input) throws MessageDataProcessingException {
        ResponseCallback<?> callback = callbackRegistry.getCallback(input.getResponseDto().getRequestId());
        try {
            RowResponse rowResponse = getRowResponse(input, callback.getResponseBodyClass());
            rowResponse.setSubscription(getSubscription(input.getResponseDto(), responseCallbackApi.getRequest(callback)));
            callback.onResponse(rowResponse);
        } catch (ResponseException e) {
            callback.onError(e);
        }

    }

    private RowResponse getRowResponse(MessageHandlerInput input, Class responseBodyClassType) throws ResponseException, MessageDataProcessingException {
        ResponseDto responseDto = input.getResponseDto();
        if (responseDto.getStatus() == RowResponseStatus.OK.getId()) {
            RowResponse rowResponse = new RowResponse();
            try {
                rowResponse.setBody(messageConverter.readJsonNode(responseDto.getBody(), responseBodyClassType));
            } catch (Exception e) {
                throw new MessageDataProcessingException(e);
            }
            rowResponse.setHeaders(responseDto.getHeaders());
            rowResponse.setRequestId(responseDto.getRequestId());
            rowResponse.setStatus(RowResponseStatus.OK);
            return rowResponse;
        }
        throw new ResponseException(responseDto.getStatus(), input.getJson());
    }

    private Subscription getSubscription(ResponseDto responseDto, RowRequest request){
        String subscriptionEventName = responseDto.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME);
        String subscriptionId = responseDto.getHeaders().get(Naming.SUBSCRIPTION_Id_HEADER_NAME);
        if(subscriptionEventName != null && subscriptionId != null)
            return new RowSubscription(subscriptionId, subscriptionEventName, request);

        return null;
    }

    private class RowSubscription implements Subscription {
        private final String subscriptionId;
        private final String subscriptionEventName;
        private final RowRequest rowRequest;

        private RowSubscription(String subscriptionId, String subscriptionEventName, RowRequest rowRequest) {
            this.subscriptionId = subscriptionId;
            this.subscriptionEventName = subscriptionEventName;
            this.rowRequest = rowRequest.clone();
            addUnsubscribeHeader(this.rowRequest, subscriptionId);
        }

        @Override
        @SneakyThrows
        public void close(RowRequest<?, ?> rowRequest, ResponseCallback<?> responseCallback) {
            this.rowRequest.getHeaders().putAll(rowRequest.getHeaders());
            this.rowRequest.setBody(rowRequest.getBody());
            this.rowRequest.setQuery(rowRequest.getQuery());
            sendRequest(this.rowRequest, responseCallback);
        }

        @Override
        @SneakyThrows
        public void close(ResponseCallback<?> responseCallback) {
            sendRequest(this.rowRequest, responseCallback);
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

    @SneakyThrows
    private void sendRequest(RowRequest rowRequest, ResponseCallback<?> responseCallback){
        String id = String.valueOf(new Random().nextInt(100));
        callbackRegistry.registerCallback(id, responseCallback);
        connectionRepository.getConnection().sendTextMessage(messageConverter.getJson(id, rowRequest));
    }

    private void addUnsubscribeHeader(RowRequest rowRequest, String subscriptionId){
        if(rowRequest.getHeaders() == null)
            rowRequest.setHeaders(new HashMap<>());
        rowRequest.getHeaders().put(Naming.UNSUBSCRIBE_HEADER_NAME, "1");
        rowRequest.getHeaders().put(Naming.SUBSCRIPTION_Id_HEADER_NAME, subscriptionId);
    }
}
