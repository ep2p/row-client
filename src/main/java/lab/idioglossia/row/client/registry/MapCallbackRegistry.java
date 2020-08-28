package lab.idioglossia.row.client.registry;

import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.exception.CallbackNotFoundException;

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
        ResponseCallback<?> callback = registry.remove(id);
        if(callback == null){
            throw new CallbackNotFoundException(id);
        }
        return callback;
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
