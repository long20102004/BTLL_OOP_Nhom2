//package com.example.demo.sse;
//
//import com.example.demo.model.MessageEntity;
//import org.springframework.http.codec.ServerSentEvent;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Sinks;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@RestController
//public class SSEHandler {
//
//    private final List<MessageEntity> messages = new ArrayList<>();
//    private final Sinks.Many<ServerSentEvent<MessageEntity>> sink = Sinks.many().multicast().onBackpressureBuffer();
//
//    @PostMapping("/send")
//    public void sendMessage(@RequestBody MessageEntity message) {
//        messages.add(message);
//        sink.tryEmitNext(ServerSentEvent.builder(message).build());
//    }
//
//    @GetMapping("/sse")
//    public Flux<ServerSentEvent<MessageEntity>> streamEvents() {
//        return sink.asFlux().mergeWith(Flux.interval(Duration.ofSeconds(1))
//                .map(sequence -> ServerSentEvent.<MessageEntity>builder()
//                        .id(String.valueOf(sequence))
//                        .event("message-event")
//                        .data(new MessageEntity("Message " + sequence))
//                        .build()));
//    }
//}