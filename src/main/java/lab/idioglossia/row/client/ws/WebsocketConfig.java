package lab.idioglossia.row.client.ws;

import lombok.*;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketConfig {
    @Builder.Default
    private long asyncSendTimeout = 20 * 60 * 1000;
    @Builder.Default
    private long maxSessionIdleTimeout = 20 * 60 * 1000;
    @Builder.Default
    private int maxBinaryMessageBufferSize = 8192 * 1000;
    @Builder.Default
    private int maxTextMessageBufferSize = 8192 * 1000;
    private SSLEngineConfigurator sslEngineConfigurator;
}
