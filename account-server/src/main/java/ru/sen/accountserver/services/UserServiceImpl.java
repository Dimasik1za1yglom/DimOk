package ru.sen.accountserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.accountserver.dao.jpa.AuthorizationDataRepository;
import ru.sen.accountserver.dao.jpa.RolesRepository;
import ru.sen.accountserver.dao.jpa.UserRepository;
import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.forms.UserForm;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;
    private final AuthorizationDataRepository dataRepository;
    private final AuthorizationDataService dataService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void addUser(UserForm userForm, String email) throws Exception {
        var user = User.builder()
                .firstName(userForm.getFirstName().strip())
                .lastName(userForm.getLastName().strip())
                .birthday(userForm.getBirthday())
                .bio(userForm.getBio().strip())
                .country(userForm.getCountry().strip())
                .city(userForm.getCity().strip())
                .phone(userForm.getPhone().strip())
                .role(rolesRepository.getReferenceById(1L))
                .build();
        try {
            AuthorizationData data = dataService.getData(email);
            addUserAndAuthorizationData(user, data);
            log.info("adding a new user by transaction was successful");
        } catch (Exception e) {
            log.error("it was not possible to add a user and update his authorization data: {}", e.getMessage());
            throw new Exception("Throwing exception for demoing rollback");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void deleteUser(Long userToDeleteId, String email) throws Exception {
        log.info("deleting a user to id {}. We start with checking for permission rights", userToDeleteId);
        try {
            AuthorizationData data = dataService.getData(email);
            User user = data.getUser();
            log.info("authorization data, who wants to delete: {}", data);
            if (userToDeleteId.equals(user.getId())
                    || (getUserById(user.getId()).getRole().getId()).equals(2L)) {
                log.info("satisfaction of the conditions for deleting the user");
                deleteUserAndAuthorizationData(userToDeleteId);
            } else {
                log.error("verification of the conditions for deleting the user failed");
                throw new Exception("Insufficient rights to delete a user");
            }
        } catch (Exception e) {
            log.error("it was not possible to add a user and update his authorization data: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void updateUser(UserForm userForm, String email) throws Exception {
        try {
            var user = User.builder()
                    .id(dataService.getData(email).getUser().getId())
                    .firstName(userForm.getFirstName().strip())
                    .lastName(userForm.getLastName().strip())
                    .birthday(userForm.getBirthday())
                    .bio(userForm.getBio().strip())
                    .country(userForm.getCountry().strip())
                    .city(userForm.getCity().strip())
                    .phone(userForm.getPhone().strip())
                    .role(rolesRepository.getReferenceById(1L))
                    .build();
            userRepository.save(user);
            log.info("transaction update: the user upate transaction was successful");
        } catch (Exception e) {
            log.error("it was not possible to add a user and update his authorization data: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean userVerification(String emailUser) {
        try {
            AuthorizationData data = dataService.getData(emailUser);
            return data.getUser() != null;
        } catch (Exception e) {
            log.error("it was not possible to Verification a user and authorization data: {}", e.getMessage());
            return false;
        }
    }

    private void deleteUserAndAuthorizationData(Long userToDeleteId) throws SQLException {
        log.info("the beginning of the transaction by delete a user and authorization data");
        dataRepository.deleteByUserId(userToDeleteId);
        userRepository.deleteById(userToDeleteId);
        log.info("successful transaction for by delete a user and authorization data");
    }


    private void addUserAndAuthorizationData(User user, AuthorizationData data) {
        log.info("the beginning of the transaction by adding a user and binding to his authorization data");
        User userAuth = userRepository.save(user);
        data.setUser(userAuth);
        dataRepository.save(data);
        log.info("successful transaction for adding a user and linking it to authorization data");
    }
}
