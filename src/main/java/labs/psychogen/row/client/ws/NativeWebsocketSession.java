package labs.psychogen.row.client.ws;


public interface NativeWebsocketSession {
    Object getNativeSession();
    <T> T getNativeSession(Class<T> var1);
}
