package lab.idioglossia.row.client.exception;

public class CallbackNotFoundException extends RuntimeException {
    public CallbackNotFoundException(String requestId) {
        super("No callback found for request id: "+ requestId);
    }
}
