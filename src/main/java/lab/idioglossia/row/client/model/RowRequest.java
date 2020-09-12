package lab.idioglossia.row.client.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RowRequest<B,Q> {
    private RowMethod method;
    private String address;
    private Q query;
    private B body;
    private Map<String, String> headers = new HashMap<>();

    public RowRequest<B,Q> clone(){
        return new RowRequestBuilder<B,Q>()
                .body(this.body)
                .query(this.query)
                .address(this.address)
                .headers(this.headers)
                .method(this.method)
                .build();
    }

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
