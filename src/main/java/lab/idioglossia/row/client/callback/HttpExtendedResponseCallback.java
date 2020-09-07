package lab.idioglossia.row.client.callback;

public interface HttpExtendedResponseCallback<E> extends ResponseCallback<E> {
    Class<E> getResponseBodyClass();
}
