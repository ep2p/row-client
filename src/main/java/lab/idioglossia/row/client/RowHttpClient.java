package lab.idioglossia.row.client;

import lab.idioglossia.row.client.callback.HttpExtendedResponseCallback;
import lab.idioglossia.row.client.model.RowRequest;

public interface RowHttpClient {
    void sendRequest(RowRequest<?, ?> rowRequest, HttpExtendedResponseCallback<?> callback);
}
