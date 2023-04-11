package ru.sen.searchserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.searchserver.entity.SearchRequest;

import java.util.List;

/**
 * The interface interacts with the search request in the database.
 */
public interface SearchRequestRepository extends JpaRepository<SearchRequest, Long> {

    void deleteByUserId(Long userId);

    List<SearchRequest> findAllByUserId(Long userId);
}
