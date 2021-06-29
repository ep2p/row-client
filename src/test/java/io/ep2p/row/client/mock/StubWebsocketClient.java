package io.ep2p.row.client.mock;

import io.ep2p.row.client.callback.ResponseCallback;
import io.ep2p.row.client.callback.SubscriptionListener;
import io.ep2p.row.client.model.RowRequest;
import io.ep2p.row.client.RequestSender;
import io.ep2p.row.client.RowClient;
import io.ep2p.row.client.RowClientConfig;
import io.ep2p.row.client.RowMessageHandler;
import lombok.SneakyThrows;

import java.io.IOException;

public class StubWebsocketClient implements RowClient {
    private final RequestSender requestSender;
    private final StubSession stubSession;
    private final RowClientConfig<StubWebsocketSession> rowClientConfig;
    private StubWebsocketSession stubWebsocketSession;

    public StubWebsocketClient(RowClientConfig<StubWebsocketSession> rowClientConfig, StubSession stubSession) {
        this.requestSender = new RequestSender(rowClientConfig.getConnectionRepository(), rowClientConfig.getMessageIdGenerator(), rowClientConfig.getCallbackRegistry(), rowClientConfig.getSubscriptionListenerRegistry(), rowClientConfig.getMessageConverter());
        this.rowClientConfig = rowClientConfig;
        this.stubSession = stubSession;
    }

    @Override
    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) throws IOException {
        requestSender.sendRequest(rowRequest, callback);
    }

    @Override
    public void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException {
        requestSender.sendSubscribe(rowRequest, callback,subscriptionListener);
    }

    @Override
    public void open() {
        RowMessageHandler<StubWebsocketSession> rowMessageHandler = rowClientConfig.getRowMessageHandlerProvider().provide(rowClientConfig, this);
        this.stubWebsocketSession = new StubWebsocketSession();
        new StubSessionMessageHandlerRegister(stubSession).register(this.stubWebsocketSession, rowMessageHandler);
        this.stubSession.open();
    }

    @SneakyThrows
    @Override
    public void close() {
        if (stubWebsocketSession != null)
            stubWebsocketSession.close(null);
    }
}
