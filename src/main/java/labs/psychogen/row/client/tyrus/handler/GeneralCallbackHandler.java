package labs.psychogen.row.client.tyrus.handler;

import labs.psychogen.row.client.callback.GeneralCallback;
import labs.psychogen.row.client.model.protocol.ResponseDto;
import labs.psychogen.row.client.pipeline.StoppablePipeline;

public class GeneralCallbackHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final GeneralCallback<?> generalCallback;

    public GeneralCallbackHandler(GeneralCallback<?> generalCallback) {
        this.generalCallback = generalCallback;
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) {
        ResponseDto responseDto = input.getResponseDto();
        generalCallback.onMessage(responseDto.getBody());
        return true;
    }
}
