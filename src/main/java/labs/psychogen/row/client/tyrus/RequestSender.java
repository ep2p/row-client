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
import labs.psychogen.row.client.ws.RowWebsocketSession;
import lombok.SneakyThrows;

import java.io.IOException;

public class RequestSender {
    private final ConnectionRepository<RowWebsocketSession> connectionRepository;
    private final MessageIdGenerator messageIdGenerator;
    private final CallbackRegistry callbackRegistry;
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final ObjectMapper objectMapper;

    public RequestSender(ConnectionRepository<RowWebsocketSession> connectionRepository, MessageIdGenerator messageIdGenerator, CallbackRegistry callbackRegistry, SubscriptionListenerRegistry subscriptionListenerRegistry) {
        this.connectionRepository = connectionRepository;
        this.messageIdGenerator = messageIdGenerator;
        this.callbackRegistry = callbackRegistry;
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        objectMapper = new ObjectMapper();
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        sendMessage(rowRequest, callback);
    }

    public <E> void sendSubscribe(RowRequest<?, ?> rowRequest, final SubscriptionCallback<E> callback, final SubscriptionListener<?> subscriptionListener) throws IOException {
        sendMessage(rowRequest, new RegistrySubscriptionCallbackDecorator<E>(callback, subscriptionListenerRegistry, subscriptionListener));
    }

    private void sendMessage(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        String messageId = messageIdGenerator.generate();
        String json = getJson(messageId, rowRequest);
        try {
            callbackRegistry.registerCallback(messageId, callback);
            connectionRepository.getConnection().sendTextMessage(json);
        }catch (RuntimeException e){
            callbackRegistry.unregisterCallback(messageId);
            throw e;
        } catch (IOException e) {
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
