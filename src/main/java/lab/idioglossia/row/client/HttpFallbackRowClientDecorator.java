package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.HttpExtendedResponseCallback;
import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.model.RowRequest;

import javax.annotation.PostConstruct;
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
            if(callback instanceof HttpExtendedResponseCallback)
                rowHttpClient.sendRequest(rowRequest, (HttpExtendedResponseCallback) callback);
        }
    }

    @Override
    @PostConstruct
    public void open() {
        super.open();
    }
}
