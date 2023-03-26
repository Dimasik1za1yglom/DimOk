package ru.sen.accountserver.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.accountserver.entity.AuthorizationData;

/**
 * The interface interacts with the user in the database.
 * <p>
 * Contains methods for working with the object AuthorizationData.class:
 * - delete object
 * - added object
 * - getting object
 */
public interface AuthorizationDataRepository extends JpaRepository<AuthorizationData, String> {

    /**
     * Deletes a string by userId
     * @param userId user id from the users table
     */
    void deleteByUserId(Long userId);


}
