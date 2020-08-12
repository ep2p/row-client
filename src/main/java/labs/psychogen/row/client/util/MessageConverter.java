package labs.psychogen.row.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.model.protocol.RequestDto;
import lombok.SneakyThrows;

public class MessageConverter {
    private final ObjectMapper objectMapper;

    public MessageConverter() {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public String getJson(String messageId, RowRequest<?, ?> rowRequest) {
        RequestDto requestDto = RequestDto.builder()
                .address(rowRequest.getAddress())
                .body(rowRequest.getBody())
                .headers(rowRequest.getHeaders())
                .method(rowRequest.getMethod().name())
                .id(messageId)
                .query(rowRequest.getQuery()).build();

        return objectMapper.writeValueAsString(requestDto);
    }
}
