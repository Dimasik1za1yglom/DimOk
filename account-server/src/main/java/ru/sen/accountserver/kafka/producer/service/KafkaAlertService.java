package ru.sen.accountserver.kafka.producer.service;

import ru.sen.accountserver.dto.UserDto;

public interface KafkaAlertService {

    void sendAlertNewUser(UserDto userDto);
}
