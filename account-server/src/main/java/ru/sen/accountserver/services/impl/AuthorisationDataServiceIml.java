package ru.sen.accountserver.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.forms.AuthorizationDataForm;
import ru.sen.accountserver.repository.AuthorizationDataRepository;
import ru.sen.accountserver.services.AuthorizationDataService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorisationDataServiceIml implements AuthorizationDataService {

    private final AuthorizationDataRepository dataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkIfEmailExists(String email) {
        String emailNotEmpty = email.strip();
        log.info("Checking existing authorization data by email: {}", emailNotEmpty);
        return dataRepository.existsById(emailNotEmpty);
    }

    @Override
    public boolean addDataWasSuccessful(AuthorizationDataForm dataForm) {
        String emailNotEmpty = dataForm.getEmail().strip();
        String passwordNotEmpty = dataForm.getPassword().strip();
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
    public boolean deleteDataWasSuccessful(Long userId) {
        log.info("delete the authorization data for userId {}: ", userId);
        try {
            dataRepository.deleteByUserId(userId);
        } catch (Exception e) {
            log.error("Delete authorization data by userId {} failed: {}", userId, e.getMessage());
            return false;
        }
        log.info("Delete of authorization data by userId {} was successful", userId);
        return true;
    }

    @Override
    public AuthorizationData getData(String email) {
        log.info("getting authorization data by email: {}", email);
        return dataRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("AuthorizationData not found"));
    }


}
