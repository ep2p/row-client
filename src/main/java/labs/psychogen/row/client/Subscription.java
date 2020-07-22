package labs.psychogen.row.client;

public interface Subscription {
    void close();
    String getId();
    String eventName();
}
