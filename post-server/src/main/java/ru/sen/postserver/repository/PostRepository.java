package ru.sen.postserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.postserver.entity.Post;

import java.util.List;

/**
 * The interface interacts with the post user in the database.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * returns all user posts
     *
     * @param userId id of the user who has posts
     * @return list of posts
     */
    List<Post> findAllByUserId(Long userId);

    /**
     * deleting all user posts
     *
     * @param userId id of the user whose posts need to be deleted
     */
    void deleteByUserId(Long userId);
}
