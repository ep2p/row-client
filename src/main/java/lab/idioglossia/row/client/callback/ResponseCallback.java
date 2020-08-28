package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.model.RowResponse;

public interface ResponseCallback<E> {
    void onResponse(RowResponse<E> rowResponse);
    void onError(Throwable throwable);
}
