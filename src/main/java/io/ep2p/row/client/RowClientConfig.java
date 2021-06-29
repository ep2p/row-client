package io.ep2p.row.client;

import io.ep2p.row.client.callback.GeneralCallback;
import io.ep2p.row.client.callback.RowTransportListener;
import io.ep2p.row.client.registry.CallbackRegistry;
import io.ep2p.row.client.registry.MapCallbackRegistry;
import io.ep2p.row.client.registry.MapSubscriptionListenerRegistry;
import io.ep2p.row.client.registry.SubscriptionListenerRegistry;
import io.ep2p.row.client.util.DefaultJacksonMessageConverter;
import io.ep2p.row.client.util.MessageConverter;
import io.ep2p.row.client.ws.HandshakeHeadersProvider;
import io.ep2p.row.client.ws.WebsocketConfig;
import io.ep2p.row.client.ws.WebsocketSession;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Data
@Builder
public class RowClientConfig<S extends WebsocketSession> {
    @Builder.Default
    private WebsocketConfig websocketConfig = new WebsocketConfig();
    private String address;
    @Builder.Default
    private HandshakeHeadersProvider handshakeHeadersProvider = new HandshakeHeadersProvider.Default();
    @Builder.Default
    private CallbackRegistry callbackRegistry = MapCallbackRegistry.Factory.getInstance();
    @Builder.Default
    private SubscriptionListenerRegistry subscriptionListenerRegistry = MapSubscriptionListenerRegistry.Factory.getInstance();
    @Builder.Default
    private MessageIdGenerator messageIdGenerator = new UUIDMessageIdGenerator();
    @Builder.Default
    private Map<String, Object> attributes = new HashMap<String, Object>();
    @Builder.Default
    private ConnectionRepository<S> connectionRepository = new ConnectionRepository.DefaultConnectionRepository<>();
    private GeneralCallback<?> generalCallback;
    private ExecutorService executorService;
    @Builder.Default
    private RowTransportListener<S> rowTransportListener = new RowTransportListener.Default<S>();
    @Builder.Default
    public MessageConverter messageConverter = new DefaultJacksonMessageConverter();
    @Builder.Default
    public RowMessageHandlerProvider<S> rowMessageHandlerProvider = new RowMessageHandlerProvider.Default<S>();
}
