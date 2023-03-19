package ru.sen.accountserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dao.AuthorizationDataRepository;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.forms.AuthorizationDataForm;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorisationDataServiceIml implements AuthorizationDataService {

    private final AuthorizationDataRepository dataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean dataVerification(AuthorizationDataForm dataForm) {
        String emailNotEmpty = dataForm.getEmail().strip();
        log.info("Checking for the presence of existing authorization data by the specified email: {}", emailNotEmpty);
        return dataRepository.getDataByEmail(emailNotEmpty).isPresent();
    }

    @Override
    public boolean addData(AuthorizationDataForm dataForm) {
        String emailNotEmpty = dataForm.getEmail().strip();
        String passwordNotEmpty = dataForm.getPassword().strip();
        var data = AuthorizationData.builder()
                .email(emailNotEmpty)
                .password(passwordEncoder.encode(passwordNotEmpty))
                .build();
        try {
            dataRepository.addAuthorizationData(data);
        } catch (Exception e) {
            log.error("Adding authorization data {} failed: {}", data, e.getMessage());
            return false;
        }
        log.info("The addition of authorization data {} was successful", data);
        return true;
    }

    @Override
    public boolean deleteData(Long userId) {
        log.info("we begin deleting the authorization data associated with the user with the id: {}", userId);
        try {
            return dataRepository.deleteDataByUserId(userId);
        } catch (Exception e) {
            log.error("could not delete authorization data by userId {}: {}", userId, e.getMessage());
            throw new IllegalArgumentException("Не удалось удалить AuthorizationData");
        }
    }

    @Override
    public AuthorizationData getData(String email) {
        log.info("getting authorization data by email: {}", email);
        return dataRepository.getDataByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("AuthorizationData not found"));
    }


}
