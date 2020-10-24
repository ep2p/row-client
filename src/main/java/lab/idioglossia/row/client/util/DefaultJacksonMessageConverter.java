package lab.idioglossia.row.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.idioglossia.row.client.model.RowRequest;
import lab.idioglossia.row.client.model.protocol.RequestDto;
import lombok.SneakyThrows;

public class DefaultJacksonMessageConverter implements MessageConverter{
    private final ObjectMapper objectMapper;

    public DefaultJacksonMessageConverter() {
        objectMapper = new ObjectMapper();
    }

    public DefaultJacksonMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    public <E> E readJsonNode(JsonNode jsonNode, Class<E> eClass) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, eClass);
    }
}
