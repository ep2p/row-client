package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.RowClient;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.tyrus.handler.PipelineFactory;
import labs.psychogen.row.client.ws.ContainerFactory;
import labs.psychogen.row.client.ws.RowClientEndpointConfig;
import labs.psychogen.row.client.ws.RowWebsocketHandlerAdapter;
import labs.psychogen.row.client.ws.RowWebsocketSession;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.concurrent.Callable;

import static labs.psychogen.row.client.model.protocol.Naming.ROW_PROTOCOL_NAME;

public class RowWebsocketClient implements RowClient {
    private final RequestSender requestSender;
    private final RowClientConfig rowClientConfig;
    private volatile RowWebsocketSession webSocketSession;

    public RowWebsocketClient(RowClientConfig rowClientConfig) {
        this.requestSender = new RequestSender(rowClientConfig.getConnectionRepository(), rowClientConfig.getMessageIdGenerator(), rowClientConfig.getCallbackRegistry(), rowClientConfig.getSubscriptionListenerRegistry());
        this.rowClientConfig = rowClientConfig;
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        requestSender.sendRequest(rowRequest, callback);
    }

    public void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException {
        requestSender.sendSubscribe(rowRequest, callback,subscriptionListener);
    }

    @SneakyThrows
    @PostConstruct
    public void open() {
        WebSocketContainer webSocketContainer = ContainerFactory.getWebSocketContainer(rowClientConfig.getWebsocketConfig());
        URI uri = URI.create(rowClientConfig.getAddress());
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().configurator(new RowClientEndpointConfig(rowClientConfig.getHandshakeHeadersProvider().getHeaders())).preferredSubprotocols(Collections.singletonList(ROW_PROTOCOL_NAME)).extensions(Collections.<Extension>emptyList()).build();
        Endpoint endpoint = new RowWebsocketHandlerAdapter(new RowWebsocketSession(rowClientConfig.getAttributes(), uri, rowClientConfig.getWebsocketConfig()), new RowMessageHandler(PipelineFactory.getPipeline(this.rowClientConfig), rowClientConfig.getConnectionRepository()));
        Callable<Void> callableContainer = getCallableContainer(webSocketContainer, uri, clientEndpointConfig, endpoint);
        if(this.rowClientConfig.getExecutorService() != null){
            this.rowClientConfig.getExecutorService().submit(callableContainer);
        }else {
            callableContainer.call();
        }
    }

    private Callable<Void> getCallableContainer(WebSocketContainer webSocketContainer, URI uri, ClientEndpointConfig clientEndpointConfig, Endpoint endpoint){
        return new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                webSocketContainer.connectToServer(endpoint, clientEndpointConfig, uri);
                return null;
            }
        };
    }

    @SneakyThrows
    public synchronized void close() {
        if (this.webSocketSession != null) {
            this.webSocketSession.close();
            this.webSocketSession = null;
        }
    }
}
