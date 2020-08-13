package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.model.PublishedMessage;

public interface SubscriptionListener<E> {
    void onMessage(Subscription subscription, PublishedMessage<E> publishedMessage);
}
