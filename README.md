# java-row-client
[![](https://jitpack.io/v/idioglossia/java-row-client.svg)](https://jitpack.io/#idioglossia/java-row-client)

Java client for ROW (Rest Over Websocket)

---

## Setup

To client to your project first include jitpack repository:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then add the dependency:

```
<dependency>
    <groupId>com.github.idioglossia</groupId>
	<artifactId>java-row-client</artifactId>
    <version>0.3.0-SNAPSHOT</version>
</dependency>
```

## Usage

Create RowClient using `RowWebsocketClient` and pass configuration object.

```
RowClient rowClient = new RowWebsocketClient(RowClientConfig.builder()
    .address("ws://localhost:8080/ws")
    .build());
```

Call `open()` method on client to start the connection:

```
rowClient.open();
```

Create a request:

```
RowRequest<BODYTYPE, QUERYTYPE> request = RowRequest.<BODYTYPE, QUERYTYPE>builder()
            .address("/address")
            .method(RowRequest.RowMethod.GET)
            .body(...)
            .query(...)
            .headers(...)
            .build();
```

Send request and pass response handler:

```
rowClient.sendRequest(request, new ResponseCallback<SampleDto>() {
        @Override
        public void onResponse(RowResponse<BODYTYPE> rowResponse) {
            System.out.println(rowResponse);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }
    });
```

If your request is subscribing to a channel, pass SubscriptionListener too:

```
rowClient.subscribe(request, new ResponseCallback<SampleDto>() {
        @Override
        public void onResponse(RowResponse<SampleDto> rowResponse) {
            //request response
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
            //subscription listener
            System.out.println(sampleDto);
            System.out.println(subscription);
        }
    });
```

Check `RowClientConfig` for more config parameters. For example you can pass handshake headers through configuration:

```
RowClient rowClient = new RowWebsocketClient(RowClientConfig.builder()
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
```

You can make changes to websocket configuration by passing `WebsocketConfig` to `RowClientConfig`. Also, its possible to alter `SSLEngineConfigurator`. Follow [this documentation](https://tyrus-project.github.io/documentation/1.13.1/user-guide.html#d0e1128).

Then on server side the websocket can be validated.
