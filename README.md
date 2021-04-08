# java-row-client
[![](https://jitpack.io/v/idioglossia/java-row-client.svg)](https://jitpack.io/#idioglossia/java-row-client)

Java client for ROW (Rest Over Websocket)

---

## Setup

To client to your project first include jitpack repository:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then add the dependency:

```xml
<dependency>
    <groupId>com.github.idioglossia</groupId>
	<artifactId>java-row-client</artifactId>
    <version>2.0.0-RELEASE</version>
</dependency>
```

## Usage

Create RowClient using `RowWebsocketClient` and pass configuration object.

```java
RowClient rowClient = new TyrusRowWebsocketClient(RowClientConfig.builder()
    .address("ws://localhost:8080/ws")
    .build());
```

Call `open()` method on client to start the connection:

```java
rowClient.open();
```

Create a request:

```java
RowRequest<BODYTYPE, QUERYTYPE> request = RowRequest.<BODYTYPE, QUERYTYPE>builder()
            .address("/address")
            .method(RowRequest.RowMethod.GET)
            .body(...)
            .query(...)
            .headers(...)
            .build();
```

Send request and pass response handler:

```java
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

```java
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

```java
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

You can make changes to websocket configuration by passing `WebsocketConfig` to `RowClientConfig`. Also, its possible to alter `SSLEngineConfigurator`. Follow [this documentation](https://eclipse-ee4j.github.io/tyrus-project.github.io/documentation/latest/index/tyrus-proprietary-config.html#d0e1129).

Then on server side the websocket can be validated.
