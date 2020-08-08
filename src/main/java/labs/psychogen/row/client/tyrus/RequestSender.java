package labs.psychogen.row.client.tyrus;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.psychogen.row.client.MessageIdGenerator;
import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.model.RowResponse;
import labs.psychogen.row.client.model.protocol.Naming;
import labs.psychogen.row.client.model.protocol.RequestDto;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import lombok.SneakyThrows;

public class RequestSender {
    private final ConnectionProvider connectionProvider;
    private final MessageIdGenerator messageIdGenerator;
    private final CallbackRegistry callbackRegistry;
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final ObjectMapper objectMapper;

    public RequestSender(ConnectionProvider connectionProvider, MessageIdGenerator messageIdGenerator, CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry) {
        this.connectionProvider = connectionProvider;
        this.messageIdGenerator = messageIdGenerator;
        this.callbackRegistry = callbackRegistry;
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        objectMapper = new ObjectMapper();
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback){
        sendMessage(rowRequest, callback);
    }

    public void sendSubscribe(RowRequest<?, ?> rowRequest, final SubscriptionCallback<?> callback, final SubscriptionListener<?> subscriptionListener){
        sendMessage(rowRequest, new SubscriptionCallback() {
            public void onResponse(RowResponse rowResponse) {
                String subscriptionEventName = getSubscriptionEventName(rowResponse);
                if(subscriptionEventName != null)
                    subscriptionListenerRegistry.registerListener(subscriptionEventName, subscriptionListener);
                callback.onResponse(rowResponse);
            }

            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }

            public Subscription getSubscription() {
                return callback.getSubscription();
            }
        });
    }

    private String getSubscriptionEventName(RowResponse rowResponse){
        return (String) rowResponse.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME);
    }

    private void sendMessage(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback){
        String messageId = messageIdGenerator.generate();
        String json = getJson(messageId, rowRequest);
        try {
            callbackRegistry.registerCallback(messageId, callback);
            connectionProvider.getSession().getAsyncRemote().sendText(json);
        }catch (RuntimeException e){
            callbackRegistry.unregisterCallback(messageId);
            throw e;
        }
    }

    @SneakyThrows
    private String getJson(String messageId, RowRequest<?, ?> rowRequest) {
        RequestDto requestDto = RequestDto.builder()
                .address(rowRequest.getAddress())
                .body(rowRequest.getBody())
                .headers(rowRequest.getHeaders())
                .method(rowRequest.getMethod().name())
                .id(messageId)
                .query(rowRequest.getQuery()).build();

        return objectMapper.writeValueAsString(requestDto);
    }

}
