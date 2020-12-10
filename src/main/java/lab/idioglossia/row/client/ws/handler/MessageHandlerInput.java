package lab.idioglossia.row.client.ws.handler;

import lab.idioglossia.row.client.model.protocol.ResponseDto;
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
