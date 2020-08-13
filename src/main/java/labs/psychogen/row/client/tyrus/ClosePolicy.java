package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.RowClient;
import lombok.SneakyThrows;

public interface ClosePolicy<C extends RowClient> {
    void execute(C client);

    class ReconnectPolicy<C extends RowClient> implements ClosePolicy<C> {
        @SneakyThrows
        @Override
        public void execute(RowClient client) {
            while (true){
                try {
                    client.open();
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                    Thread.sleep(3000);
                }
            }
        }
    }

    class IgnorePolicy<C extends RowClient> implements ClosePolicy<C> {
        @Override
        public void execute(C client) {}
    }
}
