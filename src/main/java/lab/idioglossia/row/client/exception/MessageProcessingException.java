package lab.idioglossia.row.client.exception;

public class MessageProcessingException extends Exception {
    public MessageProcessingException() {
        super();
    }

    public MessageProcessingException(String s) {
        super(s);
    }

    public MessageProcessingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MessageProcessingException(Throwable throwable) {
        super(throwable);
    }

    protected MessageProcessingException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
