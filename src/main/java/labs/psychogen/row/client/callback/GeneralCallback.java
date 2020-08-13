package labs.psychogen.row.client.callback;

public interface GeneralCallback<E> {
    <E> void onMessage(E e);
}
