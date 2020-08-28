package lab.idioglossia.row.client.tyrus.handler;

import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.PublishedMessage;
import lab.idioglossia.row.client.model.protocol.Naming;
import lab.idioglossia.row.client.model.protocol.ResponseDto;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;

public class PublisherHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;

    public PublisherHandler(SubscriptionListenerRegistry subscriptionListenerRegistry) {
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) {
        ResponseDto responseDto = input.getResponseDto();
        if(responseDto.getHeaders().containsKey(Naming.SUBSCRIPTION_EVENT_HEADER_NAME) && !responseDto.getHeaders().containsKey(Naming.SUBSCRIPTION_Id_HEADER_NAME)){
            SubscriptionListenerRegistry.SubscriptionRegistryModel<?> subscriptionRegistryModel = subscriptionListenerRegistry.getSubscriptionListener(responseDto.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME));
            SubscriptionListener<?> subscriptionListener = subscriptionRegistryModel.getSubscriptionListener();
            subscriptionListener.onMessage(subscriptionRegistryModel.getSubscription(), new PublishedMessage(responseDto.getBody()));
            return false;
        }
        return true;
    }

}
