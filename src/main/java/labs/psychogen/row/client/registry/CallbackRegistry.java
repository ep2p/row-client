package labs.psychogen.row.client.registry;

import labs.psychogen.row.client.callback.ResponseCallback;

public interface CallbackRegistry {
    void registerCallback(String id, ResponseCallback<?> responseCallback);
    void unregisterCallback(String id);
    ResponseCallback<?> getCallback(String id);
}
