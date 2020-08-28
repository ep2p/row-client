package lab.idioglossia.row.client.pipeline;

public class HandlerPipeline<I, O> {
    private final Handler<I,O> currentHandler;

    public HandlerPipeline(Handler<I, O> currentHandler) {
        this.currentHandler = currentHandler;
    }

    public <K> HandlerPipeline<I, K> addHandler(Handler<O, K> newHandler) {
        return new HandlerPipeline<>(input -> newHandler.process(currentHandler.process(input)));
    }

    public O execute(I input) {
        return currentHandler.process(input);
    }

    public interface Handler<I,O> {
        O process(I input);
    }
}
