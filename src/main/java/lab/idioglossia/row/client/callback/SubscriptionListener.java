package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.Subscription;
import lab.idioglossia.row.client.model.PublishedMessage;

public interface SubscriptionListener<E> {
    void onMessage(Subscription subscription, PublishedMessage<E> publishedMessage);
}
