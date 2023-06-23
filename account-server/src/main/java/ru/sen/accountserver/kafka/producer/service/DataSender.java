package ru.sen.accountserver.kafka.producer.service;

import ru.sen.accountserver.kafka.producer.model.AlertNewUser;

public interface DataSender {

    void send(AlertNewUser value);
}
