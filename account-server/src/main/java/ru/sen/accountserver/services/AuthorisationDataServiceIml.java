package ru.sen.accountserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sen.accountserver.dao.AuthorizationDataRepository;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.forms.AuthorizationDataForm;

@RequiredArgsConstructor
@Service
public class AuthorisationDataServiceIml implements AuthorizationDataService {

    private final AuthorizationDataRepository dataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean dataVerification(AuthorizationDataForm dataForm) {
        String emailNotEmpty = dataForm.getEmail().strip();
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
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteData(Long userId) {
        try {
            return dataRepository.deleteDataByUserId(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Не удалось удалить AuthorizationData");
        }
    }

    @Override
    public AuthorizationData getData(String email) {
        return dataRepository.getDataByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("AuthorizationData not found"));
    }


}
