package labs.psychogen.row.client.tyrus;

public interface ConnectionRepository<C> {
    C getConnection();
    void setConnection(C connection);

    class DefaultConnectionRepository<C> implements ConnectionRepository<C> {
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
