package model;

import lombok.*;

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
    private Map<String, String> headers;

    public void setStatus(RowResponseStatus status) {
        this.status = status.getId();
    }
}
