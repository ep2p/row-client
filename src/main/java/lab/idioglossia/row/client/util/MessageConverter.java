package lab.idioglossia.row.client.util;

import com.fasterxml.jackson.databind.JsonNode;
import lab.idioglossia.row.client.model.RowRequest;

public interface MessageConverter {
    String getJson(String messageId, RowRequest<?, ?> rowRequest);
    <E> E readJsonNode(JsonNode jsonNode, Class<E> eClass) throws Exception;
}
