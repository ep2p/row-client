package lab.idioglossia.row.client.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.idioglossia.row.client.model.protocol.ResponseDto;
import lab.idioglossia.row.client.model.protocol.RowResponseStatus;
import lombok.SneakyThrows;

import java.nio.ByteBuffer;

public class MockEchoSession extends MockSession {
    public MockEchoSession() {
        super(new EchoServer());
    }

    public static class EchoServer implements MockServer {
        private final ObjectMapper objectMapper;

        public EchoServer() {
            objectMapper = new ObjectMapper();
        }

        @Override
        public void onOpen(MockSession mockSession) {
            mockSession.getSessionListener().onOpen();
        }

        @Override
        public void onClose(MockSession mockSession) {
            //todo: log
        }

        @Override
        public void onPing(MockSession mockSession, ByteBuffer byteBuffer) {
            mockSession.getSessionListener().onPong(byteBuffer);
        }

        @Override
        public void onPong(MockSession mockSession, ByteBuffer byteBuffer) {
            //todo: log
        }

        @SneakyThrows
        @Override
        public void onMessage(MockSession mockSession, String message) {
            MockRequestDto requestDto = objectMapper.readValue(message, MockRequestDto.class);

            ResponseDto responseDto = ResponseDto.builder()
                    .body(requestDto.getBody())
                    .status(RowResponseStatus.OK.getId())
                    .requestId(requestDto.getId())
                    .build();

            String response = objectMapper.writeValueAsString(responseDto);

            mockSession.getSessionListener().onTextMessage(response);
        }
    }
}
