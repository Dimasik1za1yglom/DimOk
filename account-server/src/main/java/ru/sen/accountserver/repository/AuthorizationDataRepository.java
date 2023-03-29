package ru.sen.accountserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.AuthorizationData;

/**
 * The interface interacts with the user in the database.
 */
public interface AuthorizationDataRepository extends JpaRepository<AuthorizationData, String> {

    /**
     * Deletes a string by userId
     * @param userId user id from the users table
     */
    void deleteByUserId(Long userId);


}
