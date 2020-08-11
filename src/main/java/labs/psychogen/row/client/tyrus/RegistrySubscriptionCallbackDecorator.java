package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionCallbackDecorator;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.RowResponse;
import labs.psychogen.row.client.model.protocol.Naming;
import labs.psychogen.row.client.registry.SubscriptionListenerRegistry;

public class RegistrySubscriptionCallbackDecorator<E> extends SubscriptionCallbackDecorator<E> {
    private final SubscriptionListenerRegistry subscriptionListenerRegistry;
    private final SubscriptionListener<?> subscriptionListener;

    public RegistrySubscriptionCallbackDecorator(ResponseCallback<E> responseCallback, SubscriptionListenerRegistry subscriptionListenerRegistry, SubscriptionListener<?> subscriptionListener) {
        super(responseCallback);
        this.subscriptionListenerRegistry = subscriptionListenerRegistry;
        this.subscriptionListener = subscriptionListener;
    }

    @Override
    public void onResponse(RowResponse<E> rowResponse) {
        String subscriptionEventName = getSubscriptionEventName(rowResponse);
        if(isNewSubscription(rowResponse) && subscriptionEventName != null)
            subscriptionListenerRegistry.registerListener(subscriptionEventName, subscriptionListener);
        super.onResponse(rowResponse);
    }

    private boolean isNewSubscription(RowResponse<E> rowResponse){
        return rowResponse.getHeaders().containsKey(Naming.SUBSCRIPTION_Id_HEADER_NAME);
    }

    private String getSubscriptionEventName(RowResponse<E> rowResponse){
        return rowResponse.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME);
    }
}
