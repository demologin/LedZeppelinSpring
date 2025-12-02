package com.javarush.led.lesson14.note;

import com.javarush.led.lesson14.note.model.NoteEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@EnableKafka
@Component
@RequiredArgsConstructor
public class KafkaClient {

    public static final String REQUEST_TOPIC = "InTopic";
    public static final String RESPONSE_TOPIC = "OutTopic";
    private final KafkaTemplate<String, String> sender;
    private final ObjectMapper json;
    private final ConcurrentHashMap<UUID, Exchanger<NoteEvent>> kafkaCache = new ConcurrentHashMap<>();

    @SneakyThrows
    public NoteEvent sync(NoteEvent noteEvent) {
        UUID uuid = UUID.randomUUID();
        Exchanger<NoteEvent> exchanger = new Exchanger<>();
        kafkaCache.put(uuid, exchanger);
        try {
            sender.send(REQUEST_TOPIC, uuid.toString(), json.writeValueAsString(noteEvent));
            return exchanger.exchange(noteEvent, 1, TimeUnit.SECONDS);
        } catch (TimeoutException e){
            kafkaCache.remove(uuid);
            throw e;
        }
    }

    @SneakyThrows
    @KafkaListener(topics = RESPONSE_TOPIC, groupId = "groupId=#{T(java.util.UUID).randomUUID().toString()}")
    private void processNote(ConsumerRecord<String, String> record){
        UUID uuid = UUID.fromString(record.key());
        NoteEvent result = json.readValue(record.value(), NoteEvent.class);
        Exchanger<NoteEvent> exchanger = kafkaCache.remove(uuid);
        if (exchanger!=null){
            exchanger.exchange(result, 1, TimeUnit.SECONDS);
        }
    }
}
