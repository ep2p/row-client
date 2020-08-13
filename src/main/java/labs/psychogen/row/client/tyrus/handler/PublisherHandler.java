package labs.psychogen.row.client.tyrus.handler;

import labs.psychogen.row.client.model.protocol.Naming;
import labs.psychogen.row.client.model.protocol.ResponseDto;
import labs.psychogen.row.client.pipeline.StoppablePipeline;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;

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
            subscriptionRegistryModel.getSubscriptionListener().onMessage(subscriptionRegistryModel.getSubscription(), responseDto.getBody());
            return false;
        }
        return true;
    }

}
