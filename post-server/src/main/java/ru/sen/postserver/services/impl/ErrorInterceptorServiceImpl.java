package ru.sen.postserver.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.exception.PostOperationException;
import ru.sen.postserver.services.ErrorInterceptorService;
import ru.sen.postserver.services.PostService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ErrorInterceptorServiceImpl implements ErrorInterceptorService {

    private final PostService postService;

    @Override
    public boolean checkIfAddingPostSuccessful(PostDto postDto, LocalDateTime dateTime, Long userId) {
        try {
            postService.addPost(postDto, dateTime, userId);
            return true;
        } catch (PostOperationException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfDeletingPostSuccessful(Long postId) {
        try {
            postService.deletePost(postId);
            return true;
        } catch (PostOperationException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfUpdatePostSuccessful(PostDto postDto, Long postId) {
        try {
            postService.updatePost(postDto, postId);
            return true;
        } catch (PostOperationException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfDeletePostsByUsersIdSuccessful(Long userId) {
        try {
            postService.deleteAllPostByUserId(userId);
            return true;
        } catch (PostOperationException e) {
            return false;
        }
    }
}
