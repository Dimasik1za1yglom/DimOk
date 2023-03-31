package ru.sen.accountserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dto.AuthorizationDataDto;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.exception.runtime.DataNotFoundException;
import ru.sen.accountserver.repository.AuthorizationDataRepository;
import ru.sen.accountserver.services.AuthorizationDataService;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationDataServiceImpl implements AuthorizationDataService {

    private final AuthorizationDataRepository dataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkIfEmailExists(String email) {
        String emailNotEmpty = email.strip();
        log.info("Checking existing authorization data by email: {}", emailNotEmpty);
        return dataRepository.existsById(emailNotEmpty);
    }

    @Override
    public boolean addDataWasSuccessful(AuthorizationDataDto dataDto) {
        String emailNotEmpty = dataDto.getEmail().strip();
        String passwordNotEmpty = dataDto.getPassword().strip();
        var data = AuthorizationData.builder()
                .email(emailNotEmpty)
                .password(passwordEncoder.encode(passwordNotEmpty))
                .build();
        try {
            dataRepository.save(data);
        } catch (Exception e) {
            log.error("Adding authorization data {} failed: {}", data, e.getMessage());
            return false;
        }
        log.info("Adding authorization data {} was successful", data);
        return true;
    }

    @Override
    public void deleteDataByUserId(Long userId) {
        log.info("delete the authorization data for userId {}: ", userId);
        dataRepository.deleteByUserId(userId);
        log.info("Delete of authorization data by userId {} was successful", userId);
    }

    @Override
    public AuthorizationData getData(String email) {
        log.info("getting authorization data by email: {}", email);
        return dataRepository.findById(email)
                .orElseThrow(() -> new DataNotFoundException("AuthorizationData not found"));
    }

    @Override
    public void updateData(String email, User user) {
        log.info("update authorization data by email: {}, adding attachment use {}", email, user);
        AuthorizationData data = getData(email);
        data.setUser(user);
        dataRepository.save(data);
        log.info("update authorization data was successful");
    }

    @Override
    public boolean checkIfUserExists(String emailUser) {
        try {
            return Objects.isNull(getData(emailUser));
        } catch (Exception e) {
            log.error("Not possible to Verification a user and authorization data: {}", e.getMessage());
            return false;
        }
    }


}
