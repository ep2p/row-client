package labs.psychogen.row.client.ws;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class WebsocketConfig {
    private long asyncSendTimeout;
    private long maxSessionIdleTimeout;
    private int maxBinaryMessageBufferSize;
    private int maxTextMessageBufferSize;
}
