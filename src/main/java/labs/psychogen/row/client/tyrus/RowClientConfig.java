package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.MessageIdGenerator;
import labs.psychogen.row.client.callback.GeneralCallback;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.MapCallbackRegistry;
import labs.psychogen.row.client.registry.MapSubscriptionListenerRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import labs.psychogen.row.client.ws.HandshakeHeadersProvider;
import labs.psychogen.row.client.ws.RowWebsocketSession;
import labs.psychogen.row.client.ws.WebsocketConfig;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class RowClientConfig {
    private WebsocketConfig websocketConfig;
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
    private ConnectionRepository<RowWebsocketSession> connectionRepository = new ConnectionRepository.DefaultConnectionRepository<>();
    @Builder.Default
    private GeneralCallback<?> generalCallback;
}
