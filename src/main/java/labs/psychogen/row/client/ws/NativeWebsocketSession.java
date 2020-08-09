package labs.psychogen.row.client.ws;

import org.springframework.lang.Nullable;

public interface NativeWebsocketSession {
    Object getNativeSession();

    @Nullable
    <T> T getNativeSession(@Nullable Class<T> var1);
}
