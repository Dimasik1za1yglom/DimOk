package ru.sen.searchserver.kafka.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sen.searchserver.kafka.consumer.model.AlertNewUser;

import java.util.List;

@Slf4j
@Service
public class KafkaAlertServiceImpl implements KafkaAlertService{

    @Override
    public void receivingAlertNewUser(List<AlertNewUser> values) {
        log.info("Получен новый пользователь {}", values);
    }
}
