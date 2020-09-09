package lab.idioglossia.row.client.tyrus.handler;

import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.protocol.Naming;
import lab.idioglossia.row.client.model.protocol.ResponseDto;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.registry.SubscriptionListenerRegistry;
import lab.idioglossia.row.client.util.MessageConverter;
import lombok.SneakyThrows;

public class PublisherHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final MessageConverter messageConverter;

    public PublisherHandler(SubscriptionListenerRegistry subscriptionListenerRegistry, MessageConverter messageConverter) {
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.messageConverter = messageConverter;
    }

    @SneakyThrows
    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) {
        ResponseDto responseDto = input.getResponseDto();
        if(responseDto.getHeaders().containsKey(Naming.SUBSCRIPTION_EVENT_HEADER_NAME) && !responseDto.getHeaders().containsKey(Naming.SUBSCRIPTION_Id_HEADER_NAME)){
            SubscriptionListenerRegistry.SubscriptionRegistryModel<?> subscriptionRegistryModel = subscriptionListenerRegistry.getSubscriptionListener(responseDto.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME));
            SubscriptionListener subscriptionListener = subscriptionRegistryModel.getSubscriptionListener();
            subscriptionListener.onMessage(subscriptionRegistryModel.getSubscription(), messageConverter.readJsonNode(responseDto.getBody(), subscriptionListener.getListenerMessageBodyClassType()));
            return false;
        }
        return true;
    }

}
