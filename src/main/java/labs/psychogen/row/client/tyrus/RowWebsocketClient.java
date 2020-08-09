package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.RowClient;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.ws.ContainerFactory;
import labs.psychogen.row.client.ws.RowClientEndpointConfig;
import labs.psychogen.row.client.ws.RowWebsocketHandlerAdapter;
import labs.psychogen.row.client.ws.RowWebsocketSession;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static labs.psychogen.row.client.model.protocol.Naming.ROW_PROTOCOL_NAME;

public class RowWebsocketClient implements RowClient, ConnectionProvider {
    private final RequestSender requestSender;
    private final RowConfig rowConfig;

    public RowWebsocketClient(RowConfig rowConfig) {
        this.requestSender = new RequestSender(this, rowConfig.getMessageIdGenerator(), rowConfig.getCallbackRegistry(), rowConfig.getSubscriptionListenerRegistry());
        this.rowConfig = rowConfig;
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        requestSender.sendRequest(rowRequest, callback);
    }

    public void subscribe(RowRequest<?, ?> rowRequest, SubscriptionCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException {
        requestSender.sendSubscribe(rowRequest, callback,subscriptionListener);
    }

    @PostConstruct
    public void init() {
        WebSocketContainer webSocketContainer = ContainerFactory.getWebSocketContainer(rowConfig.getWebsocketConfig());
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().configurator(new RowClientEndpointConfig(rowConfig.getHandshakeHeadersProvider().getHeaders())).preferredSubprotocols(Collections.singletonList(ROW_PROTOCOL_NAME)).extensions(Collections.<Extension>emptyList()).build();
        Endpoint endpoint = new RowWebsocketHandlerAdapter(new RowWebsocketSession(rowConfig.getAttributes(), URI.create(rowConfig.getAddress()), rowConfig.getWebsocketConfig()), null);
    }

    public void close() {

    }

    public WebSocketSession getSession() {
        return null;
    }
}
