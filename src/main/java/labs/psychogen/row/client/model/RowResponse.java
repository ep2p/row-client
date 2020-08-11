package labs.psychogen.row.client.model;

import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.model.protocol.RowResponseStatus;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RowResponse<E> {
    private String requestId;
    private E body;
    private int status;
    @Builder.Default
    private Map<String, String> headers = new HashMap<String, String>();
    private Subscription subscription;

    public void setStatus(RowResponseStatus status) {
        this.status = status.getId();
    }
}
