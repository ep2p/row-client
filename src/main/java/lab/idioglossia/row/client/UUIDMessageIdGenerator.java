package lab.idioglossia.row.client;

import java.util.UUID;

public class UUIDMessageIdGenerator implements MessageIdGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
