package example;

import labs.psychogen.row.client.RowClient;
import labs.psychogen.row.client.Subscription;
import labs.psychogen.row.client.callback.ResponseCallback;
import labs.psychogen.row.client.callback.SubscriptionListener;
import labs.psychogen.row.client.model.PublishedMessage;
import labs.psychogen.row.client.model.RowRequest;
import labs.psychogen.row.client.model.RowResponse;
import labs.psychogen.row.client.tyrus.RowClientConfig;
import labs.psychogen.row.client.tyrus.TyrusRowWebsocketClient;
import labs.psychogen.row.client.ws.HandshakeHeadersProvider;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Example1 {
    @SneakyThrows
    public static void main(String[] args) {
        RowClient rowClient = new TyrusRowWebsocketClient(RowClientConfig.builder()
                .address("ws://localhost:8080/ws")
                .handshakeHeadersProvider(new HandshakeHeadersProvider() {
                    @Override
                    public Map<String, List<String>> getHeaders() {
                        Map<String, List<String>> headers = new HashMap<>();
                        headers.put("X-Auth-Token", Collections.singletonList("adminToken"));
                        return headers;
                    }
                })
                .build());
        rowClient.open();

        //t1
        RowRequest<SampleDto, SampleDto> request = RowRequest.<SampleDto, SampleDto>builder()
                .address("/t1")
                .method(RowRequest.RowMethod.GET)
                .build();
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>() {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });


        //t2
        request.setAddress("/t2");
        request.setMethod(RowRequest.RowMethod.POST);
        request.setBody(new SampleDto("alter me :P "));
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>() {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });


        //t3
        request.setAddress("/t3");
        request.setQuery(new SampleDto("This is my query"));
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>() {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        //t4
        request.setAddress("/t4/hello");
        request.setMethod(RowRequest.RowMethod.GET);
        request.setQuery(null);
        request.setBody(null);
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>() {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });


        //subs/t1
        request.setAddress("/subs/t1");
        rowClient.subscribe(request, new ResponseCallback<SampleDto>() {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
                System.out.println(rowResponse.getSubscription());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, new SubscriptionListener<SampleDto>() {
            @Override
            public void onMessage(Subscription subscription, PublishedMessage<SampleDto> sampleDto) {
                System.out.println(sampleDto);
                System.out.println(subscription);
            }
        });

        Thread.sleep(1000);

        //publish
        request.setAddress("/subs/publish/t1");
        request.setMethod(RowRequest.RowMethod.POST);
        request.setBody(new SampleDto("publish me :P "));
        rowClient.sendRequest(request, new ResponseCallback<SampleDto>() {
            @Override
            public void onResponse(RowResponse<SampleDto> rowResponse) {
                System.out.println(rowResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Thread.sleep(5000);
    }
}