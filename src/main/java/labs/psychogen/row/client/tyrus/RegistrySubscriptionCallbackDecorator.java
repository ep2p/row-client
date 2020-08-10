package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.callback.SubscriptionCallback;
import labs.psychogen.row.client.callback.SubscriptionCallbackDecorator;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowResponse;
import labs.psychogen.row.client.model.protocol.Naming;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;

public class RegistrySubscriptionCallbackDecorator<E> extends SubscriptionCallbackDecorator<E> {
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final SubscriptionListener<?> subscriptionListener;

    public RegistrySubscriptionCallbackDecorator(SubscriptionCallback<E> subscriptionCallback, SubscriptionListenerRegistry subscriptionListenerRegistry, SubscriptionListener<?> subscriptionListener) {
        super(subscriptionCallback);
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.subscriptionListener = subscriptionListener;
    }

    @Override
    public void onResponse(RowResponse<E> rowResponse) {
        String subscriptionEventName = getSubscriptionEventName(rowResponse);
        if(subscriptionEventName != null)
            subscriptionListenerRegistry.registerListener(subscriptionEventName, subscriptionListener);
        super.onResponse(rowResponse);
    }

    private String getSubscriptionEventName(RowResponse<E> rowResponse){
        return rowResponse.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME);
    }
}
