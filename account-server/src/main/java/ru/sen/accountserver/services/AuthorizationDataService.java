package ru.sen.accountserver.services;

import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.forms.AuthorizationDataForm;

/**
 * the interface is designed to implement business logic for working with authorization data and,
 * if possible, to catch errors if requests through repositories were not successful
 */
public interface AuthorizationDataService {

    /**
     * checking for the presence in the database of a user with such an email
     * @param dataForm contains the email and password of a possible new user
     * @return returns true if such a user exists, and false if not
     */
    boolean verifyData(AuthorizationDataForm dataForm);

    /**
     * creates an Authorization Data object from the received form,
     * and sends a request through the repositories to the database
     *  When saving for the first time, the userId field is null
     * @param dataForm contains the email and password of a possible new user
     * @return true if the user's authorization data was saved, and false if not
     */
    boolean addData(AuthorizationDataForm dataForm);

    /**
     * deletes the user's authorization data by his Id,
     * after which the user himself and the users tables must be deleted
     * @param userId id of the user who is being deleted
     * @return true if the user was deleted, false if not
     */
    boolean deleteData(Long userId);

    /**
     * getting authorization data by email
     * @param email primary key
     * @return the object of authorization data. Should the null check pass inside,
     * otherwise, throw an exception or process it immediately
     */
    AuthorizationData getData(String email);
}
