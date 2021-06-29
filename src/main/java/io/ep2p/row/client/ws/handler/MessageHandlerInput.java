package io.ep2p.row.client.ws.handler;

import io.ep2p.row.client.model.protocol.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageHandlerInput {
    private final String json;
    private ResponseDto responseDto;

    public MessageHandlerInput(String json) {
        this.json = json;
    }
}
