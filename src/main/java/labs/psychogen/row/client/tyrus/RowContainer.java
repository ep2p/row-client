package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.MessageIdGenerator;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.MapCallbackRegistry;
import labs.psychogen.row.client.registry.MapSubscriptionListenerRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import labs.psychogen.row.client.ws.HandshakeHeadersProvider;
import labs.psychogen.row.client.ws.WebsocketConfig;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class RowContainer {
    private WebsocketConfig websocketConfig;
    private String address;
    @Builder.Default
    private HandshakeHeadersProvider handshakeHeadersProvider;
    @Builder.Default
    private CallbackRegistry callbackRegistry = MapCallbackRegistry.Factory.getInstance();
    @Builder.Default
    private SubscriptionListenerRegistry subscriptionListenerRegistry = MapSubscriptionListenerRegistry.Factory.getInstance();
    @Builder.Default
    private MessageIdGenerator messageIdGenerator = new UUIDMessageIdGenerator();
    @Builder.Default
    private Map<String, Object> attributes = new HashMap<String, Object>();
}
