package ru.sen.accountserver.kafka.producer.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.kafka.producer.service.DataSender;
import ru.sen.accountserver.kafka.producer.service.KafkaAlertService;
import ru.sen.accountserver.mappers.AlertUserMapper;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaAlertServiceImpl implements KafkaAlertService {

    private final DataSender dataSender;
    private final AlertUserMapper alertUserMapper;

    @PostConstruct
    public void init() {
        dataSender.send(alertUserMapper.userDtoToAlertNewUser(UserDto.builder()
                .firstName("Dima")
                .lastName("Krasil")
                .phone("+79803638899")
                .bio("bio")
                .city("city")
                .birthday(LocalDate.now())
                .country("country")
                .build()));
    }

    @Override
    public void sendAlertNewUser(UserDto userDto) {
        log.info("start send alert new user {} by producer in search service", userDto);
        dataSender.send(alertUserMapper.userDtoToAlertNewUser(userDto));
    }
}
