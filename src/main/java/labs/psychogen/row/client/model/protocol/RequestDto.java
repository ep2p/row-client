package labs.psychogen.row.client.model.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private String id;
    private String method;
    private String address;
    private Object query;
    private Object body;
    private Map<String, String> headers;
    @Builder.Default
    private Double version = 1.0;
}
