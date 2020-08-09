package labs.psychogen.row.client.registry;

import labs.psychogen.row.client.callback.ResponseCallback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapCallbackRegistry implements CallbackRegistry {
    private final Map<String, ResponseCallback<?>> registry = new ConcurrentHashMap<String, ResponseCallback<?>>();

    public void registerCallback(String id, ResponseCallback<?> responseCallback) {
        registry.put(id, responseCallback);
    }

    public void unregisterCallback(String id) {
        registry.remove(id);
    }

    public ResponseCallback<?> getCallback(String id) {
        return registry.remove(id);
    }

    public static class Factory {
        private static MapCallbackRegistry mapCallbackRegistry;

        private Factory(){}

        public static synchronized MapCallbackRegistry getInstance(){
            if(mapCallbackRegistry == null){
                mapCallbackRegistry = new MapCallbackRegistry();
            }
            return mapCallbackRegistry;
        }
    }
}
