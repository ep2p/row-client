package labs.psychogen.row.client.ws;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@ToString
public class WebsocketConfig {
    private long asyncSendTimeout;
    private long maxSessionIdleTimeout;
    private int maxBinaryMessageBufferSize;
    private int maxTextMessageBufferSize;
}
