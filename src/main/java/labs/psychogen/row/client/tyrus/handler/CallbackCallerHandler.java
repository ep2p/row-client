package labs.psychogen.row.client.tyrus.handler;

import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.exceptions.ResponseException;
import labs.psychogen.row.client.model.RowResponse;
import labs.psychogen.row.client.model.protocol.Naming;
import labs.psychogen.row.client.model.protocol.ResponseDto;
import labs.psychogen.row.client.model.protocol.RowResponseStatus;
import labs.psychogen.row.client.pipeline.Pipeline;
import labs.psychogen.row.client.registry.CallbackRegistry;

public class CallbackCallerHandler implements Pipeline.Handler<MessageHandlerInput, Void> {
    private final CallbackRegistry callbackRegistry;

    public CallbackCallerHandler(CallbackRegistry callbackRegistry) {
        this.callbackRegistry = callbackRegistry;
    }

    @Override
    public Void process(MessageHandlerInput input) {
        ResponseDto responseDto = input.getResponseDto();
        if (responseDto.getRequestId() == null) {
            return null;
        }
        callCallback(responseDto, input);
        return null;
    }

    private void callCallback(ResponseDto responseDto, MessageHandlerInput input) {
        ResponseCallback<?> callback = callbackRegistry.getCallback(responseDto.getRequestId());
        if (responseDto.getStatus() == RowResponseStatus.OK.getId()) {
            callback.onResponse(getRowResponse(responseDto));
        }else {
            callback.onError(new ResponseException(responseDto.getStatus(), input.getJson()));
        }
    }

    private RowResponse getRowResponse(ResponseDto responseDto) {
        RowResponse rowResponse = new RowResponse();
        rowResponse.setBody(responseDto.getBody());
        rowResponse.setHeaders(responseDto.getHeaders());
        rowResponse.setRequestId(responseDto.getRequestId());
        rowResponse.setStatus(RowResponseStatus.OK);
        rowResponse.setSubscription(getSubscription(responseDto));
        return rowResponse;
    }

    private Subscription getSubscription(ResponseDto responseDto){
        String subscriptionEventName = responseDto.getHeaders().get(Naming.SUBSCRIPTION_EVENT_HEADER_NAME);
        String subscriptionId = responseDto.getHeaders().get(Naming.SUBSCRIPTION_Id_HEADER_NAME);
        if(subscriptionEventName != null && subscriptionId != null)
            return new Subscription() {
                @Override
                public void close() {
                    //todo
                }

                @Override
                public String getId() {
                    return subscriptionId;
                }

                @Override
                public String eventName() {
                    return subscriptionEventName;
                }
            };

        return null;
    }
}
