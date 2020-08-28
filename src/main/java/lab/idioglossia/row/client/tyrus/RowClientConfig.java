package lab.idioglossia.row.client.tyrus;

import lab.idioglossia.row.client.MessageIdGenerator;
import lab.idioglossia.row.client.callback.GeneralCallback;
import lab.idioglossia.row.client.callback.RowTransportListener;
import lab.idioglossia.row.client.registry.CallbackRegistry;
import lab.idioglossia.row.client.registry.MapCallbackRegistry;
import lab.idioglossia.row.client.registry.MapSubscriptionListenerRegistry;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;
import lab.idioglossia.row.client.ws.HandshakeHeadersProvider;
import lab.idioglossia.row.client.ws.RowWebsocketSession;
import lab.idioglossia.row.client.ws.WebsocketConfig;
import lombok.Builder;
import lombok.Data;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Data
@Builder
public class RowClientConfig {
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
    private ConnectionRepository<RowWebsocketSession> connectionRepository = new ConnectionRepository.DefaultConnectionRepository<>();
    @Builder.Default
    private GeneralCallback<?> generalCallback;
    private ExecutorService executorService;
    @Builder.Default
    private RowTransportListener rowTransportListener = new RowTransportListener.Default();
    private SSLContext sslContext;
    {
        try {
            sslContext = SSLContext.getDefault();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
