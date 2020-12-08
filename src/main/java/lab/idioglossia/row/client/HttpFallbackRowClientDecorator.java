package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.ResponseCallback;
import lab.idioglossia.row.client.callback.SubscriptionListener;
import lab.idioglossia.row.client.model.RowRequest;

import java.io.IOException;

public class HttpFallbackRowClientDecorator extends RowClientDecorator {
    private final RowHttpClient rowHttpClient;
    private volatile boolean switched = false;

    public HttpFallbackRowClientDecorator(RowClient rowClient, RowHttpClient rowHttpClient) {
        super(rowClient);
        this.rowHttpClient = rowHttpClient;
    }

    @Override
    public void sendRequest(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback) {
        if(!switched){
            try {
                super.sendRequest(rowRequest, callback);
                return;
            }catch (IOException | IllegalStateException ignored){
                switched = true;
            }
        }
        rowHttpClient.sendRequest(rowRequest, callback);
    }

    @Override
    public void subscribe(RowRequest<?, ?> rowRequest, ResponseCallback<?> callback, SubscriptionListener<?> subscriptionListener) throws IOException {
        if(switched){
            throw new IOException("Cant send subscribe on HTTP Fallback client");
        }
        super.subscribe(rowRequest, callback, subscriptionListener);
    }

    @Override
    public void open() {
        try {
            super.open();
        }catch (Exception e){
            switched = true;
        }
    }
}
