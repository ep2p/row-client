package labs.psychogen.row.client.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HandshakeHeadersProvider {
    Map<String, List<String>> getHeaders();

    class Default implements HandshakeHeadersProvider {
        @Override
        public Map<String, List<String>> getHeaders() {
            return new HashMap<>();
        }
    }
}
