package lab.idioglossia.row.client.tyrus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.idioglossia.row.client.callback.GeneralCallback;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.registry.CallbackRegistry;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;
import lab.idioglossia.row.client.tyrus.ConnectionRepository;
import lab.idioglossia.row.client.tyrus.RowClientConfig;
import lab.idioglossia.row.client.ws.RowWebsocketSession;

public class PipelineFactory {

    public static StoppablePipeline<MessageHandlerInput, Void> getPipeline(RowClientConfig rowClientConfig){
        return getPipeline(rowClientConfig.getCallbackRegistry(), rowClientConfig.getConnectionRepository(), rowClientConfig.getSubscriptionListenerRegistry(), rowClientConfig.getGeneralCallback());
    }

    public static StoppablePipeline<MessageHandlerInput, Void> getPipeline(
            CallbackRegistry callbackRegistry,
            ConnectionRepository<RowWebsocketSession> connectionRepository,
            SubscriptionListenerRegistry subscriptionListenerRegistry,
            GeneralCallback<?> generalCallback
    ){
        StoppablePipeline<MessageHandlerInput, Void> pipeline = new StoppablePipeline<>();
        return pipeline.addStage(new ConvertToResponseDtoHandler(new ObjectMapper()))
                .addStage(new CallbackCallerHandler(callbackRegistry, connectionRepository))
                .addStage(new PublisherHandler(subscriptionListenerRegistry, messageConverter))
                .addStage(new GeneralCallbackHandler(generalCallback));
    }

}
