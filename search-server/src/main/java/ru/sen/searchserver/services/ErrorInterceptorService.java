package ru.sen.searchserver.services;

/**
 * the repository was created to intercept errors that may occur in services with errors in transactions
 * it is an intermediate link to remove error handling from controllers
 */
public interface ErrorInterceptorService {

    /**
     * checking that the all seacrh requests user to delete transaction was successful
     *
     * @param userId id of the user whose search requests need to be deleted
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfDeletingSearchRequestSuccessful(Long userId);
}
