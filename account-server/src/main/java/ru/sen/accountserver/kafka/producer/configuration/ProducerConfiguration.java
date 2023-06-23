package ru.sen.accountserver.kafka.producer.configuration;


import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import ru.sen.accountserver.kafka.producer.model.AlertNewUser;
import ru.sen.accountserver.kafka.producer.service.DataSender;
import ru.sen.accountserver.kafka.producer.service.impl.DataSenderImpl;

@Slf4j
@Configuration
public class ProducerConfiguration {

    public final String topicName;

    public ProducerConfiguration(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, AlertNewUser> producerFactory(KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, AlertNewUser>(props);
        kafkaProducerFactory.setValueSerializer(new org.springframework.kafka.support.serializer.JsonSerializer<>(mapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, AlertNewUser> kafkaTemplate(
            ProducerFactory<String, AlertNewUser> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        log.info("create bean topic by name: {}", topicName);
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }

    @Bean
    public DataSender dataSender(NewTopic topic, KafkaTemplate<String, AlertNewUser> kafkaTemplate) {
        log.info("create DataSender by topic: {}", topic);
        return new DataSenderImpl(kafkaTemplate,
                topic.name()) {
        };
    }
}
