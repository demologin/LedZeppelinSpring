package com.javarush.led.lesson15.note;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

    public static final String RESPONSE_TOPIC = "OutTopic";
    private static final Duration REPLY_TIMEOUT = Duration.ofSeconds(5);

    // 1. Бин ReplyingKafkaTemplate
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(
            ProducerFactory<String, String> producerFactory,
            // Использование конкретного типа, который предоставляет Spring
            ConcurrentMessageListenerContainer<String, String> replyContainer) { 
        
        ReplyingKafkaTemplate<String, String, String> template = 
                new ReplyingKafkaTemplate<>(producerFactory, replyContainer);
        
        template.setDefaultReplyTimeout(REPLY_TIMEOUT);
        
        return template;
    }

    // 2. Бин ConcurrentMessageListenerContainer для прослушивания ответов
    @Bean
    public ConcurrentMessageListenerContainer<String, String> replyContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> containerFactory,
            ConsumerFactory<String, String> consumerFactory) {
        
        containerFactory.setConsumerFactory(consumerFactory);
        
        // createContainer() фактически возвращает ConcurrentMessageListenerContainer
        ConcurrentMessageListenerContainer<String, String> container = 
                (ConcurrentMessageListenerContainer<String, String>) containerFactory.createContainer(RESPONSE_TOPIC);
        
        ContainerProperties containerProperties = container.getContainerProperties();
        containerProperties.setMissingTopicsFatal(false);
        containerProperties.setGroupId("reply-group-" + java.util.UUID.randomUUID().toString());
        containerProperties.setPollTimeout(100);
        
        return container;
    }
}