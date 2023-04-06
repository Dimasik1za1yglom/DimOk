package ru.sen.postserver.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.entity.Post;
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

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addPost(PostDto postDto, LocalDateTime dateTime, Long userId) {

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deletePost(Long postId) {

    }

    @Override
    public List<Post> getAllPostByUserId(Long userId) {
        return null;
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updatePost(PostDto postDto, Long userId) {

    }
}
