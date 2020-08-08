package labs.psychogen.row.client.tyrus;

import labs.psychogen.row.client.MessageIdGenerator;

import java.util.UUID;

public class UUIDMessageIdGenerator implements MessageIdGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
