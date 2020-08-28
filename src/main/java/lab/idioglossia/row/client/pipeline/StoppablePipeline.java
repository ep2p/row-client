package lab.idioglossia.row.client.pipeline;

import java.util.ArrayList;
import java.util.List;

public class StoppablePipeline<I, O> {
    private final List<Stage<I, O>> stages;

    public StoppablePipeline() {
        this.stages = new ArrayList<>();
    }

    public StoppablePipeline(Stage<I,O> stage){
        this();
        addStage(stage);
    }

    public StoppablePipeline<I, O> addStage(Stage<I,O> stage){
        stages.add(stage);
        return this;
    }

    public void execute(I input, O output){
        assert stages.size() > 0;
        int i = 0;
        while (i < stages.size() && stages.get(i).process(input, output)){
            i++;
        }
    }

    public interface Stage<I, O> {
        boolean process(I input, O output);
    }
}
