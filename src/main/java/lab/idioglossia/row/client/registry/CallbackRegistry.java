package lab.idioglossia.row.client.registry;

import lab.idioglossia.row.client.callback.ResponseCallback;

public interface CallbackRegistry {
    void registerCallback(String id, ResponseCallback<?> responseCallback);
    void unregisterCallback(String id);
    ResponseCallback<?> getCallback(String id);
}
