package ru.sen.postserver.services;

import ru.sen.postserver.dto.PostDto;

import java.time.LocalDateTime;

/**
 * the repository was created to intercept errors that may occur in services with errors in transactions
 * it is an intermediate link to remove error handling from controllers
 */
public interface ErrorInterceptorService {

    /**
     * checking that the post addition transaction was successful
     *
     * @param postDto the object to be added
     * @param dateTime time to create a post
     * @param userId id of the user who has posts
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfAddingPostSuccessful(PostDto postDto, LocalDateTime dateTime, Long userId);

    /**
     * checking that the post to delete transaction was successful
     *
     * @param postId id post primary key
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfDeletingPostSuccessful(Long postId);

    /**
     * checking for a successful transaction to update the post with exception interception
     *
     * @param postDto the object to be update
     * @param postId id post primary key
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfUpdatePostSuccessful(PostDto postDto, Long postId);
}
