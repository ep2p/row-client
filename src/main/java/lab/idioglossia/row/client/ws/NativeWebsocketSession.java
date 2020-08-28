package lab.idioglossia.row.client.ws;


public interface NativeWebsocketSession {
    Object getNativeSession();
    <T> T getNativeSession(Class<T> var1);
}
