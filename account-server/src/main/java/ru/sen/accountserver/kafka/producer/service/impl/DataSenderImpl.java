package ru.sen.accountserver.kafka.producer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.sen.accountserver.kafka.producer.model.AlertNewUser;
import ru.sen.accountserver.kafka.producer.service.DataSender;

@Slf4j
@RequiredArgsConstructor
public class DataSenderImpl implements DataSender {

    private final KafkaTemplate<String, AlertNewUser> template;

    private final String topic;

    @Override
    public void send(AlertNewUser value) {
        try {
            log.info("value:{}", value);
            template.send(topic, value)
                    .whenComplete((result, ex) -> {
                                if (ex == null) {
                                    log.info("message alert new user:{} was sent, offset:{}", value,
                                            result.getRecordMetadata().offset());
                                    //TODO: что-то надо делать если отправлилось сообщение в топик успешно?
                                } else {
                                    log.error("message alert new user: {} was not sent", value, ex);
                                }
                            });
        } catch (Exception ex) {
            log.error("send error, value:{}", value, ex);
        }
    }
}

