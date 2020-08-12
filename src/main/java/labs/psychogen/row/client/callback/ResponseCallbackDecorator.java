package labs.psychogen.row.client.callback;

import labs.psychogen.row.client.model.RowResponse;

public class ResponseCallbackDecorator<E> implements ResponseCallback<E> {
    private final ResponseCallback<E> responseCallback;

    public ResponseCallbackDecorator(ResponseCallback<E> responseCallback) {
        this.responseCallback = responseCallback;
    }

    @Override
    public void onResponse(RowResponse<E> rowResponse) {
        responseCallback.onResponse(rowResponse);
    }

    @Override
    public void onError(Throwable throwable) {
        responseCallback.onError(throwable);
    }
}
