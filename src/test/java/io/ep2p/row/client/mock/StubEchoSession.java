package io.ep2p.row.client.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ep2p.row.client.model.protocol.ResponseDto;
import io.ep2p.row.client.model.protocol.RowResponseStatus;
import lombok.SneakyThrows;

import java.nio.ByteBuffer;

public class StubEchoSession extends StubSession {
    public StubEchoSession() {
        super(new EchoServer());
    }

    public static class EchoServer implements MockServer {
        private final ObjectMapper objectMapper;

        public EchoServer() {
            objectMapper = new ObjectMapper();
        }

        @Override
        public void onOpen(StubSession stubSession) {
            stubSession.getSessionListener().onOpen();
        }

        @Override
        public void onClose(StubSession stubSession) {
            //todo: log
        }

        @Override
        public void onPing(StubSession stubSession, ByteBuffer byteBuffer) {
            stubSession.getSessionListener().onPong(byteBuffer);
        }

        @Override
        public void onPong(StubSession stubSession, ByteBuffer byteBuffer) {
            //todo: log
        }

        @SneakyThrows
        @Override
        public void onMessage(StubSession stubSession, String message) {
            StubRequestDto requestDto = objectMapper.readValue(message, StubRequestDto.class);

            ResponseDto responseDto = ResponseDto.builder()
                    .body(requestDto.getBody())
                    .status(RowResponseStatus.OK.getId())
                    .requestId(requestDto.getId())
                    .build();

            String response = objectMapper.writeValueAsString(responseDto);

            stubSession.getSessionListener().onTextMessage(response);
        }
    }
}
