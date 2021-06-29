package io.ep2p.row.client.callback;

import io.ep2p.row.client.Subscription;

import java.lang.reflect.ParameterizedType;

public interface SubscriptionListener<E> {
     default Class<E> getListenerMessageBodyClassType(){
        return  ((Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    void onMessage(Subscription subscription, E message);
}
