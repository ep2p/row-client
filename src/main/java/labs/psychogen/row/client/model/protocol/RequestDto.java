package labs.psychogen.row.client.model.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private String id;
    private String method;
    private String address;
    private JsonNode query;
    private JsonNode body;
    private Map<String, String> headers;
    private Double version = 1.0;
}
