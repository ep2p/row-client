package labs.psychogen.row.client.exception;

import lombok.Getter;

@Getter
public class ResponseException extends Exception {
    private final int status;
    private final String json;

    public ResponseException(int status, String json) {
        super("Request failed with status code "+ status + " body: " + json);
        this.status = status;
        this.json = json;
    }
}
