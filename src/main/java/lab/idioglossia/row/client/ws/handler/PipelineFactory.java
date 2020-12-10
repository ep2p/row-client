package lab.idioglossia.row.client.ws.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lab.idioglossia.row.client.callback.GeneralCallback;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.registry.CallbackRegistry;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;
import lab.idioglossia.row.client.tyrus.ConnectionRepository;
import lab.idioglossia.row.client.tyrus.RowClientConfig;
import lab.idioglossia.row.client.util.MessageConverter;
import lab.idioglossia.row.client.ws.WebsocketSession;

public class PipelineFactory {

    public static <S extends WebsocketSession> StoppablePipeline<MessageHandlerInput, Void> getPipeline(RowClientConfig<S> rowClientConfig){
        return getPipeline(rowClientConfig.getCallbackRegistry(), rowClientConfig.getConnectionRepository(), rowClientConfig.getSubscriptionListenerRegistry(), rowClientConfig.getGeneralCallback(), rowClientConfig.getMessageConverter());
    }

    public static <S extends WebsocketSession> StoppablePipeline<MessageHandlerInput, Void> getPipeline(
            CallbackRegistry callbackRegistry,
            ConnectionRepository<S> connectionRepository,
            SubscriptionListenerRegistry subscriptionListenerRegistry,
            GeneralCallback<?> generalCallback,
            MessageConverter messageConverter
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        StoppablePipeline<MessageHandlerInput, Void> pipeline = new StoppablePipeline<>();
        return pipeline.addStage(new ConvertToResponseDtoHandler(objectMapper))
                .addStage(new CallbackCallerHandler(callbackRegistry, connectionRepository, messageConverter))
                .addStage(new PublisherHandler(subscriptionListenerRegistry, messageConverter))
                .addStage(new GeneralCallbackHandler(generalCallback));
    }

}
