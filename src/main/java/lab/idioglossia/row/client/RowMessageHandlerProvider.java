package lab.idioglossia.row.client;

import lab.idioglossia.row.client.tyrus.RowClientConfig;
import lab.idioglossia.row.client.tyrus.RowMessageHandler;
import lab.idioglossia.row.client.ws.WebsocketSession;
import lab.idioglossia.row.client.ws.handler.PipelineFactory;

public interface RowMessageHandlerProvider<S extends WebsocketSession> {
    RowMessageHandler<S> provide(RowClientConfig<S> clientConfig, RowClient rowClient);

    class Default<S extends WebsocketSession> implements RowMessageHandlerProvider<S>{

        @Override
        public RowMessageHandler<S> provide(RowClientConfig<S> clientConfig, RowClient rowClient) {
            return new RowMessageHandler<S>(PipelineFactory.getPipeline(clientConfig), clientConfig.getConnectionRepository(), clientConfig.getRowTransportListener(), rowClient);
        }
    }
}
