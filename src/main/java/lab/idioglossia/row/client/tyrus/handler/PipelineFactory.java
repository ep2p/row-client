package lab.idioglossia.row.client.tyrus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.idioglossia.row.client.callback.GeneralCallback;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.registry.CallbackRegistry;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;
import lab.idioglossia.row.client.tyrus.ConnectionRepository;
import lab.idioglossia.row.client.tyrus.RowClientConfig;
import lab.idioglossia.row.client.util.DefaultJacksonMessageConverter;
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
        DefaultJacksonMessageConverter defaultJacksonMessageConverter = new DefaultJacksonMessageConverter();
        return pipeline.addStage(new ConvertToResponseDtoHandler(new ObjectMapper()))
                .addStage(new CallbackCallerHandler(callbackRegistry, connectionRepository, defaultJacksonMessageConverter))
                .addStage(new PublisherHandler(subscriptionListenerRegistry, defaultJacksonMessageConverter))
                .addStage(new GeneralCallbackHandler(generalCallback));
    }

}
