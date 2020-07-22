import model.RowResponse;

public interface ResponseCallback<E> {
    void onResponse(RowResponse<E> rowResponse);
}
