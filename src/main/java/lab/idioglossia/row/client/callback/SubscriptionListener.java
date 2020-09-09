package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.Subscription;

public abstract class SubscriptionListener<E> {
    private final Class<E> eClass;

    protected SubscriptionListener(Class<E> listenerMessageBody) {
        this.eClass = listenerMessageBody;
    }

    public final Class<E> getListenerMessageBodyClassType(){
        return this.eClass;
    }

    public abstract void onMessage(Subscription subscription, E message);
}
