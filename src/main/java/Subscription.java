public interface Subscription {
    void close();
    String getId();
    String eventName();
}
