package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.RowResponse;

public abstract class ResponseCallback<E> {
    private final Class<E> eClass;
    private RowRequest rowRequest;

    public ResponseCallback(Class<E> responseBodyClass) {
        this.eClass = responseBodyClass;
    }

    public final Class<E> getResponseBodyClass(){
        return this.eClass;
    }

    private RowRequest getRowRequest() {
        return rowRequest;
    }

    private void setRowRequest(RowRequest rowRequest) {
        this.rowRequest = rowRequest;
    }

    public abstract void onResponse(RowResponse<E> rowResponse);
    public abstract void onError(Throwable throwable);

    public static class API {
        public void setRequest(ResponseCallback responseCallback, RowRequest rowRequest){
            responseCallback.setRowRequest(rowRequest);
        }

        public RowRequest getRequest(ResponseCallback responseCallback){
            return responseCallback.getRowRequest();
        }
    }
}
