package ru.sen.searchserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.searchserver.entity.SearchRequest;

/**
 * The interface interacts with the search request in the database.
 */
public interface SearchRequestRepository extends JpaRepository<SearchRequest, Long> {
}
