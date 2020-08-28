package lab.idioglossia.row.client.tyrus;

import lab.idioglossia.row.client.MessageIdGenerator;

import java.util.UUID;

public class UUIDMessageIdGenerator implements MessageIdGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
