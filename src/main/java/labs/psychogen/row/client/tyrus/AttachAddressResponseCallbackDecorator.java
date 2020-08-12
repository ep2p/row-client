package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.ResponseCallbackDecorator;

public class AttachAddressResponseCallbackDecorator<E> extends ResponseCallbackDecorator<E> {
    private final String address;

    public AttachAddressResponseCallbackDecorator(ResponseCallback<E> responseCallback, String address) {
        super(responseCallback);
        this.address = address;
    }

    @Override
    public String getAddress() {
        return this.address;
    }
}
