package labs.psychogen.row.client;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.model.RowRequest;

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
        }catch (IOException e){
            rowHttpClient.sendRequest(rowRequest, callback);
        }
    }

    @Override
    @PostConstruct
    public void open() {
        super.open();
    }
}
