package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.model.RowResponse;

public interface ResponseCallback<E> {
    void onResponse(RowResponse<E> rowResponse);
}
