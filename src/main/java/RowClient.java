import model.RowRequest;

import java.util.List;

public interface RowClient {
    void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback);
    void subscribe(RowRequest<?, ?> rowRequest, SubscriptionCallback<?> callback, SubscriptionListener<?> subscriptionListener);
    List<Subscription> getSubscriptions();
    Subscription getSubscription(String eventName);
    void close();
}
