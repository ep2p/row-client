package io.ep2p.row.client.ws.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ep2p.row.client.exception.MessageDataProcessingException;
import io.ep2p.row.client.model.protocol.ResponseDto;
import io.ep2p.row.client.pipeline.StoppablePipeline;

public class ConvertToResponseDtoHandler implements StoppablePipeline.Stage<MessageHandlerInput, Void> {
    private final ObjectMapper objectMapper;

    public ConvertToResponseDtoHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean process(MessageHandlerInput input, Void aVoid) throws MessageDataProcessingException {
        ResponseDto responseDto = null;
        try {
            responseDto = objectMapper.readValue(input.getJson(), ResponseDto.class);
            if(!responseDto.getType().equals("response"))
                return false;
            input.setResponseDto(responseDto);
            return true;
        } catch (JsonProcessingException e) {
            throw new MessageDataProcessingException(e);
        }
    }
}
