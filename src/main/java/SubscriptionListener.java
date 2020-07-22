public interface SubscriptionListener<E> {
    void onMessage(Subscription subscription, E e);
}
