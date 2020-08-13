package labs.psychogen.row.client.tyrus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.psychogen.row.client.model.protocol.ResponseDto;
import labs.psychogen.row.client.pipeline.HandlerPipeline;
import lombok.SneakyThrows;

public class ConvertToResponseDtoHandler implements HandlerPipeline.Handler<MessageHandlerInput, Void> {
    private final ObjectMapper objectMapper;

    public ConvertToResponseDtoHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public Void process(MessageHandlerInput input) {
        ResponseDto responseDto = objectMapper.readValue(input.getJson(), ResponseDto.class);
        input.setResponseDto(responseDto);
        return null;
    }
}
