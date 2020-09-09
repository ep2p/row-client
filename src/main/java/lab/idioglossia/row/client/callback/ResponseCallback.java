package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.model.RowResponse;

public abstract class ResponseCallback<E> {
    private final Class<E> eClass;

    public ResponseCallback(Class<E> responseBodyClass) {
        this.eClass = responseBodyClass;
    }

    public final Class<E> getResponseBodyClass(){
        return this.eClass;
    }

    public abstract void onResponse(RowResponse<E> rowResponse);
    public abstract void onError(Throwable throwable);
}
