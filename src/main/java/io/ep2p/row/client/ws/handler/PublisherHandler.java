package io.ep2p.row.client.ws.handler;

import io.ep2p.row.client.callback.SubscriptionListener;
import io.ep2p.row.client.exception.MessageDataProcessingException;
import io.ep2p.row.client.model.protocol.Naming;
import io.ep2p.row.client.model.protocol.ResponseDto;
import io.ep2p.row.client.pipeline.StoppablePipeline;
import io.ep2p.row.client.registry.SubscriptionListenerRegistry;
import io.ep2p.row.client.util.MessageConverter;

public class PublisherHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final MessageConverter messageConverter;

    public PublisherHandler(SubscriptionListenerRegistry subscriptionListenerRegistry, MessageConverter messageConverter) {
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.messageConverter = messageConverter;
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) throws MessageDataProcessingException {
        ResponseDto responseDto = input.getResponseDto();
        if(responseDto.getHeaders().containsKey(Naming.SUBSCRIPTION_EVENT_HEADER_NAME) && !responseDto.getHeaders().containsKey(Naming.SUBSCRIPTION_Id_HEADER_NAME)){
            SubscriptionListenerRegistry.SubscriptionRegistryModel<?> subscriptionRegistryModel = subscriptionListenerRegistry.getSubscriptionListener(responseDto.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME));
            SubscriptionListener subscriptionListener = subscriptionRegistryModel.getSubscriptionListener();
            try {
                subscriptionListener.onMessage(subscriptionRegistryModel.getSubscription(), messageConverter.readJsonNode(responseDto.getBody(), subscriptionListener.getListenerMessageBodyClassType()));
            } catch (Exception e) {
                throw new MessageDataProcessingException(e);
            }
            return false;
        }
        return true;
    }

}
