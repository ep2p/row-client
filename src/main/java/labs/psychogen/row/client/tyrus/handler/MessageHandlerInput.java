package labs.psychogen.row.client.tyrus.handler;

import labs.psychogen.row.client.model.protocol.ResponseDto;
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
