package io.ep2p.row.client.registry;

import io.ep2p.row.client.callback.ResponseCallback;

public interface CallbackRegistry {
    void registerCallback(String id, ResponseCallback<?> responseCallback);
    void unregisterCallback(String id);
    ResponseCallback<?> getCallback(String id);
}
