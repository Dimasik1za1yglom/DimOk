package ru.sen.postserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.postserver.entity.Post;

/**
 * The interface interacts with the post user in the database.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
