package ru.sen.searchserver.kafka.consumer.service;

import ru.sen.searchserver.kafka.consumer.model.AlertNewUser;

import java.util.List;

public interface KafkaAlertService {

    void receivingAlertNewUser(List<AlertNewUser> values);
}
