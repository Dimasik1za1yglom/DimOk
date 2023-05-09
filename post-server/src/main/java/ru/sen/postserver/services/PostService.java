package ru.sen.postserver.services;

import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.entity.Post;
import ru.sen.postserver.exception.PostOperationException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * the service is designed to work with an object of the Post class, receiving, adding, deleting and updating objects
 */
public interface PostService {

    /**
     * creating a post and saving it to a database
     *
     * @param postDto contains data for saving a new post
     * @param userId  id of the user who creates the post
     */
    void addPost(PostDto postDto, LocalDateTime dateTime, Long userId) throws PostOperationException;

    /**
     * deleting a post from the database by its id
     *
     * @param postId id of the post to delete
     */
    void deletePost(Long postId) throws PostOperationException;

    /**
     * returns all user posts
     *
     * @param userId id of the user whose posts
     * @return list of user posts
     */
    List<Post> getAllPostByUserId(Long userId);

    /**
     * we get the post by its id
     *
     * @param postId post id primary key
     * @return the user's post that needs to be returned
     */
    Post getPostById(Long postId);

    /**
     * updating the post with its changes in the database
     *
     * @param postDto contains data for update a post
     * @param postId  id post who update
     */
    void updatePost(PostDto postDto, Long postId) throws PostOperationException;

    /**
     * Deleting all user posts
     * @param userId id of the user whose posts should be deleted
     */
    void deleteAllPostByUserId(Long userId) throws PostOperationException;
}
