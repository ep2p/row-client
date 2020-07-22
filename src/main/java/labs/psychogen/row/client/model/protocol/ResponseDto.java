package labs.psychogen.row.client.model.protocol;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseDto {
    private String requestId;
    private Object body;
    private int status;
    private Map<String, String> headers;

    public void setStatus(RowResponseStatus status) {
        this.status = status.getId();
    }
}
