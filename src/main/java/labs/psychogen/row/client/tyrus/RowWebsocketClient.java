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
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static labs.psychogen.row.client.model.protocol.Naming.ROW_PROTOCOL_NAME;

public class RowWebsocketClient implements RowClient, ConnectionProvider {
    private final RequestSender requestSender;
    private final RowContainer rowContainer;
    private volatile RowWebsocketSession webSocketSession;

    public RowWebsocketClient(RowContainer rowContainer) {
        this.requestSender = new RequestSender(this, rowContainer.getMessageIdGenerator(), rowContainer.getCallbackRegistry(), rowContainer.getSubscriptionListenerRegistry());
        this.rowContainer = rowContainer;
    }

    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        requestSender.sendRequest(rowRequest, callback);
    }

    public void subscribe(RowRequest<?, ?> rowRequest, SubscriptionCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException {
        requestSender.sendSubscribe(rowRequest, callback,subscriptionListener);
    }

    @PostConstruct
    public void open() {
        WebSocketContainer webSocketContainer = ContainerFactory.getWebSocketContainer(rowContainer.getWebsocketConfig());
        URI uri = URI.create(rowContainer.getAddress());
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().configurator(new RowClientEndpointConfig(rowContainer.getHandshakeHeadersProvider().getHeaders())).preferredSubprotocols(Collections.singletonList(ROW_PROTOCOL_NAME)).extensions(Collections.<Extension>emptyList()).build();
        Endpoint endpoint = new RowWebsocketHandlerAdapter(new RowWebsocketSession(rowContainer.getAttributes(), uri, rowContainer.getWebsocketConfig()), new RowMessageHandler(new RowMessageHandler.Listener() {
            public void onOpen(RowWebsocketSession rowWebsocketSession) {
                setWebSocketSession(rowWebsocketSession);
            }

            public void onClose(RowWebsocketSession rowWebsocketSession, CloseReason closeReason) {

            }
        }, rowContainer.getCallbackRegistry(), rowContainer.getSubscriptionListenerRegistry()));
        try {
            webSocketContainer.connectToServer(endpoint, clientEndpointConfig, uri);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public synchronized void close() {
        if (this.webSocketSession != null) {
            this.webSocketSession.close();
            this.webSocketSession = null;
        }
    }

    public RowWebsocketSession getSession() {
        return webSocketSession;
    }

    private void setWebSocketSession(RowWebsocketSession webSocketSession){
        this.webSocketSession = webSocketSession;
    }
}
