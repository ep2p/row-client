package lab.idioglossia.row.client.callback;

public interface GeneralCallback<E> {
    Class<E> getClassOfCallback();
    void onMessage(E e);
}
