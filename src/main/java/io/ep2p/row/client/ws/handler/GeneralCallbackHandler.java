package io.ep2p.row.client.ws.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ep2p.row.client.callback.GeneralCallback;
import io.ep2p.row.client.exception.MessageDataProcessingException;
import io.ep2p.row.client.model.protocol.ResponseDto;
import io.ep2p.row.client.pipeline.StoppablePipeline;

public class GeneralCallbackHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final ObjectMapper objectMapper;
    private final GeneralCallback generalCallback;

    public GeneralCallbackHandler(ObjectMapper objectMapper, GeneralCallback<?> generalCallback) {
        this.objectMapper = objectMapper;
        this.generalCallback = generalCallback;
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) throws MessageDataProcessingException {
        ResponseDto responseDto = input.getResponseDto();
        try {
            Object o = objectMapper.readValue(responseDto.getBody().toString(), generalCallback.getClassOfCallback());
            generalCallback.onMessage(o);
            return true;
        } catch (JsonProcessingException e) {
            throw new MessageDataProcessingException(e);
        }
    }
}
