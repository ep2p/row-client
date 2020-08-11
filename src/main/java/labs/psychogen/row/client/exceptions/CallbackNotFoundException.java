package labs.psychogen.row.client.exceptions;

public class CallbackNotFoundException extends RuntimeException {
    public CallbackNotFoundException(String requestId) {
        super("No callback found for request id: "+ requestId);
    }
}
