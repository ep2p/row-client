package io.ep2p.row.client;


import io.ep2p.row.client.callback.ResponseCallback;
import io.ep2p.row.client.model.RowRequest;
import io.ep2p.row.client.model.RowResponse;
import io.ep2p.row.client.mock.StubEchoSession;
import io.ep2p.row.client.mock.StubWebsocketClient;
import io.ep2p.row.client.mock.StubWebsocketSession;
import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class RowClientTest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MockDto {
        private String key;
        private String value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MockDto mockDto = (MockDto) o;
            return Objects.equals(getKey(), mockDto.getKey()) &&
                    Objects.equals(getValue(), mockDto.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getValue());
        }
    }

    @SneakyThrows
    @Test
    public void testEchoServer(){
        RowClient rowClient = new StubWebsocketClient(RowClientConfig.<StubWebsocketSession>builder().build(), new StubEchoSession());
        rowClient.open();

        String key = "key";
        String value = "value";


        MockDto mockDto = MockDto.builder().key(key).value(value).build();
        RowRequest<MockDto, String> request = RowRequest.<MockDto, String>builder()
                .method(RowRequest.RowMethod.POST)
                .body(mockDto)
                .build();


        AtomicReference<RowResponse<MockDto>> atomicReference = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        rowClient.sendRequest(request, new ResponseCallback<MockDto>() {

            @Override
            public void onResponse(RowResponse<MockDto> rowResponse) {
                countDownLatch.countDown();
                atomicReference.set(rowResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
                throw new RuntimeException(throwable);
            }
        });

        countDownLatch.await();
        RowResponse<MockDto> mockDtoRowResponse = atomicReference.get();
        Assertions.assertNotNull(mockDtoRowResponse);
        Assertions.assertEquals(mockDtoRowResponse.getBody(), mockDto);

    }

}
