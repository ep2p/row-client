package labs.psychogen.row.client.tyrus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.psychogen.row.client.callback.GeneralCallback;
import labs.psychogen.row.client.pipeline.StoppablePipeline;
import labs.psychogen.row.client.registry.CallbackRegistry;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;
import labs.psychogen.row.client.tyrus.ConnectionRepository;
import labs.psychogen.row.client.tyrus.RowClientConfig;
import labs.psychogen.row.client.ws.RowWebsocketSession;

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
                .addStage(new PublisherHandler(subscriptionListenerRegistry))
                .addStage(new GeneralCallbackHandler(generalCallback));
    }

}
