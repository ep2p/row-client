package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.Subscription;

import java.lang.reflect.ParameterizedType;

public interface SubscriptionListener<E> {
     default Class<E> getListenerMessageBodyClassType(){
        return  ((Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    void onMessage(Subscription subscription, E message);
}
