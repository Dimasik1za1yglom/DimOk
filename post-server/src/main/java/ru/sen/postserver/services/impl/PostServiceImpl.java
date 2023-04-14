package ru.sen.postserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.entity.Post;
import ru.sen.postserver.exception.PostOperationException;
import ru.sen.postserver.mappers.PostMapper;
import ru.sen.postserver.repository.PostRepository;
import ru.sen.postserver.services.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = PostOperationException.class)
    public void addPost(PostDto postDto, LocalDateTime dateTime, Long userId) throws PostOperationException {
        log.info("add post: {} by userId: {}", postDto, userId);
        try {
            postRepository.save(postMapper.postDtoToPost(postDto, dateTime, userId));
            log.info("Adding a new post by userId {} was successful", userId);
        } catch (Exception e) {
            log.error("Adding a new post by userId {} is failed: {}", userId, e.getMessage());
            throw new PostOperationException("Throwing exception for demoing rollback");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = PostOperationException.class)
    public void deletePost(Long postId) throws PostOperationException {
        log.info("delete post by id {}", postId);
        try {
            postRepository.deleteById(postId);
            log.info("Deleting post by id {} was successful", postId);
        } catch (Exception e) {
            log.error("Delete post by id {} is failed: {}", postId, e.getMessage());
            throw new PostOperationException("Throwing exception for demoing rollback");
        }
    }

    @Override
    public List<Post> getAllPostByUserId(Long userId) {
        log.info("get list<Post> by userId: {}", userId);
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public Post getPostById(Long postId) {
        log.info("get post by id: {}", postId);
        return postRepository.getReferenceById(postId);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = PostOperationException.class)
    public void updatePost(PostDto postDto, Long postId) throws PostOperationException {
        log.info("update post {}, by Id {}", postDto, postId);
        try {
            Post oldPost = getPostById(postId);
            Post updatePost = postMapper.postDtoToPost(postDto, oldPost.getTimeCreation(), oldPost.getUserId());
            updatePost.setId(postId);
            postRepository.save(updatePost);
            log.info("update post by id {} was successful", postId);
        } catch (Exception e) {
            log.error("update post by userId is failed: {}", e.getMessage());
            throw new PostOperationException("Throwing exception for demoing rollback");
        }

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = PostOperationException.class)
    public void deleteAllPostByUserId(Long userId) throws PostOperationException {
        log.info("delete all post by user id {}", userId);
        try {
            postRepository.deleteByUserId(userId);
            log.info("Deleting all post by user id {} was successful", userId);
        } catch (Exception e) {
            log.error("Delete all post by user id {} is failed: {}", userId, e.getMessage());
            throw new PostOperationException("Throwing exception for demoing rollback");
        }
    }
}
