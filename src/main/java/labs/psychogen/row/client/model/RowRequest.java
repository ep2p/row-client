package labs.psychogen.row.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowRequest<B,Q> {
    private RowMethod method;
    private String address;
    private Q query;
    private B body;
    private Map<String, String> headers;

    @Getter
    public enum RowMethod {
        GET("get"), POST("post"), FETCH("fetch"), PUT("put"), PATCH("patch"), DELETE("delete");
        private final String name;

        RowMethod(String name) {
            this.name = name;
        }
    }

    public static <B,Q> RowRequest<B, Q> getDefault(final String address, final RowMethod rowMethod){
        RowRequest<B, Q> rowRequest = new RowRequest<>();
        rowRequest.setAddress(address);
        rowRequest.setMethod(rowMethod);
        return rowRequest;
    }
}
