package ru.sen.searchserver.kafka.consumer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.Payload;
import ru.sen.searchserver.kafka.consumer.model.AlertNewUser;
import ru.sen.searchserver.kafka.consumer.service.KafkaAlertService;

import java.util.List;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@Slf4j
@Configuration
public class ConsumerConfiguration {

    public final String topicName;

    public ConsumerConfiguration(@Value("${application.kafka.topic}")String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, AlertNewUser> consumerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, AlertNewUser.class);

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, AlertNewUser>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(mapper));
        return kafkaConsumerFactory;
    }

    @Bean("listenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, AlertNewUser>>
    listenerContainerFactory(ConsumerFactory<String, AlertNewUser> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, AlertNewUser>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);
//
//        var executor = new SimpleAsyncTaskExecutor("k-consumer-");
//        executor.setConcurrencyLimit(10);
//        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
//        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }

    @Bean
    public KafkaClient kafkaClientConsumer(KafkaAlertService alertService) {
        return new KafkaClient(alertService);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }



    public static class KafkaClient {
        private final KafkaAlertService alertService;

        public KafkaClient(KafkaAlertService alertService) {
            this.alertService = alertService;
        }

        @KafkaListener(
                topics = "${application.kafka.topic}",
                containerFactory = "listenerContainerFactory")
        public void listen(@Payload List<AlertNewUser> values) {
            log.info("get new users {})",values );
            alertService.receivingAlertNewUser(values);
        }
    }
}
