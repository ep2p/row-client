package io.ep2p.row.client.ws.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.ep2p.row.client.ws.WebsocketSession;
import io.ep2p.row.client.ConnectionRepository;
import io.ep2p.row.client.RowClientConfig;
import io.ep2p.row.client.callback.GeneralCallback;
import io.ep2p.row.client.pipeline.StoppablePipeline;
import io.ep2p.row.client.registry.CallbackRegistry;
import io.ep2p.row.client.registry.SubscriptionListenerRegistry;
import io.ep2p.row.client.util.MessageConverter;

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
                .addStage(new GeneralCallbackHandler(objectMapper, generalCallback));
    }

}
