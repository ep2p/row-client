package lab.idioglossia.row.client.tyrus.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.idioglossia.row.client.exception.MessageDataProcessingException;
import lab.idioglossia.row.client.model.protocol.ResponseDto;
import lab.idioglossia.row.client.pipeline.StoppablePipeline;

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
            input.setResponseDto(responseDto);
            return true;
        } catch (JsonProcessingException e) {
            throw new MessageDataProcessingException(e);
        }
    }
}
