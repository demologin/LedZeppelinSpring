package com.javarush.led.lesson15.note;

import com.javarush.led.lesson15.note.model.NoteEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyMessageFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class KafkaClient {

    public static final String REQUEST_TOPIC = "InTopic";
    public static final String RESPONSE_TOPIC = "OutTopic";
    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
    private final ObjectMapper json;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1);

    public Mono<NoteEvent> sync(NoteEvent noteEvent) {
        String correlationId = java.util.UUID.randomUUID().toString();
        
        Message<String> requestMessage;
        try {
             requestMessage = MessageBuilder
                    .withPayload(json.writeValueAsString(noteEvent))
                    .setHeader(KafkaHeaders.TOPIC, REQUEST_TOPIC)
                    .setHeader(KafkaHeaders.KEY, correlationId)
                    .setHeader(KafkaHeaders.CORRELATION_ID, correlationId.getBytes())
                    .setHeader(KafkaHeaders.REPLY_TOPIC, RESPONSE_TOPIC.getBytes())
                    .build();
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Serialization error", e));
        }

        RequestReplyMessageFuture<String, String> answer = replyingKafkaTemplate.sendAndReceive(requestMessage);
        try {
            Message<?> message = answer.get();
            String payload = message.getPayload().toString();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return Mono.fromFuture(replyingKafkaTemplate.sendAndReceive(requestMessage))
                .timeout(DEFAULT_TIMEOUT)
                .map(reply -> this.convertReplyToNoteEvent((Message<?>) reply))
                .onErrorMap(TimeoutException.class, e -> new TimeoutException("Kafka request timed out"));
    }
    
    private NoteEvent convertReplyToNoteEvent(Message<?> replyMessage) {
        String payload = (String) replyMessage.getPayload();
        
        try {
            return json.readValue(payload, NoteEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Deserialization error for payload: " + payload, e);
        }
    }
}