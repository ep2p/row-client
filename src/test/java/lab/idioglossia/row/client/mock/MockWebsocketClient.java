package lab.idioglossia.row.client.mock;

import lab.idioglossia.row.client.RequestSender;
import lab.idioglossia.row.client.RowClient;
import lab.idioglossia.row.client.RowClientConfig;
import lab.idioglossia.row.client.RowMessageHandler;
import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.RowRequest;
import lombok.SneakyThrows;

import java.io.IOException;

public class MockWebsocketClient implements RowClient {
    private final RequestSender requestSender;
    private final MockSession mockSession;
    private final RowClientConfig<MockWebsocketSession> rowClientConfig;
    private MockWebsocketSession mockWebsocketSession;

    public MockWebsocketClient(RowClientConfig<MockWebsocketSession> rowClientConfig, MockSession mockSession) {
        this.requestSender = new RequestSender(rowClientConfig.getConnectionRepository(), rowClientConfig.getMessageIdGenerator(), rowClientConfig.getCallbackRegistry(), rowClientConfig.getSubscriptionListenerRegistry(), rowClientConfig.getMessageConverter());
        this.rowClientConfig = rowClientConfig;
        this.mockSession = mockSession;
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
        RowMessageHandler<MockWebsocketSession> rowMessageHandler = rowClientConfig.getRowMessageHandlerProvider().provide(rowClientConfig, this);
        this.mockWebsocketSession = new MockWebsocketSession();
        new MockSessionMessageHandlerRegister(mockSession).register(this.mockWebsocketSession, rowMessageHandler);
        this.mockSession.open();
    }

    @SneakyThrows
    @Override
    public void close() {
        if (mockWebsocketSession != null)
            mockWebsocketSession.close(null);
    }
}
