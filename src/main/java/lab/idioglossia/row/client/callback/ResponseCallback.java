package lab.idioglossia.row.client.callback;

import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.RowResponse;

import java.lang.reflect.ParameterizedType;

public abstract class ResponseCallback<E> {
    private RowRequest rowRequest;

    public final Class<E> getResponseBodyClass(){
        return  ((Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected RowRequest getRowRequest() {
        return rowRequest;
    }

    protected void setRowRequest(RowRequest rowRequest) {
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
