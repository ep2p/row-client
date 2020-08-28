package lab.idioglossia.row.client.tyrus.handler;

import lab.idioglossia.row.client.pipeline.StoppablePipeline;
import lab.idioglossia.row.client.callback.GeneralCallback;
import lab.idioglossia.row.client.model.protocol.ResponseDto;

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
