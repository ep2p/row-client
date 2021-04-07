package lab.idioglossia.row.client.mock;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MockRequestDto {
    private String id;
    private String method;
    private String address;
    private Object query;
    private JsonNode body;
    private Map<String, String> headers;
    @Builder.Default
    private Double version = 1.0;
    @Builder.Default
    private String type = "request";
}
