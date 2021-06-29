package io.ep2p.row.client;

import java.util.UUID;

public class UUIDMessageIdGenerator implements MessageIdGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
