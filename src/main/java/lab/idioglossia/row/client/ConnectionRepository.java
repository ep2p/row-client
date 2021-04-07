package lab.idioglossia.row.client;

import lab.idioglossia.row.client.ws.WebsocketSession;

public interface ConnectionRepository<C extends WebsocketSession> {
    C getConnection();
    void setConnection(C connection);

    class DefaultConnectionRepository<C extends WebsocketSession> implements ConnectionRepository<C> {
        private volatile C connection;

        @Override
        public C getConnection() {
            return this.connection;
        }

        @Override
        public void setConnection(C connection) {
            this.connection = connection;
        }
    }
}
