package io.ep2p.row.client.exception;

public class MessageDataProcessingException extends Exception {
    public MessageDataProcessingException() {
        super();
    }

    public MessageDataProcessingException(String s) {
        super(s);
    }

    public MessageDataProcessingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MessageDataProcessingException(Throwable throwable) {
        super(throwable);
    }

    protected MessageDataProcessingException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
