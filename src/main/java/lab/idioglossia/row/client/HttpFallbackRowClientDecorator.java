package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.model.RowRequest;

import java.io.IOException;

public class HttpFallbackRowClientDecorator extends RowClientDecorator {
    private final RowHttpClient rowHttpClient;

    public HttpFallbackRowClientDecorator(RowClient rowClient, RowHttpClient rowHttpClient) {
        super(rowClient);
        this.rowHttpClient = rowHttpClient;
    }

    @Override
    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) {
        try {
            super.sendRequest(rowRequest, callback);
        }catch (IOException | IllegalStateException e){
            rowHttpClient.sendRequest(rowRequest, callback);
        }
    }

    @Override
    public void open() {
        super.open();
    }
}
