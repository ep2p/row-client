package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.RowResponse;

public abstract class ResponseCallbackDecorator<E> extends ResponseCallback<E> {
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

    @Override
    protected RowRequest getRowRequest() {
        return responseCallback.getRowRequest();
    }

    @Override
    protected void setRowRequest(RowRequest rowRequest) {
        responseCallback.setRowRequest(rowRequest);
    }
}
