package labs.psychogen.row.client.ws;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RowClientEndpointConfig extends javax.websocket.ClientEndpointConfig.Configurator {
    private final Map<String, List<String>> headers;

    public RowClientEndpointConfig(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        headers.putAll(this.headers);
        headers.put("Sec-WebSocket-Protocol", Collections.singletonList("row-protocol"));
    }
}
