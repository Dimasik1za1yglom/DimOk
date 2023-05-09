package ru.sen.searchserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.searchserver.entity.SearchRequest;

import java.util.List;

/**
 * The interface interacts with the search request in the database.
 */
public interface SearchRequestRepository extends JpaRepository<SearchRequest, Long> {

    /**
     * checking whether the user's search query history exists in the database
     *
     * @param userId id of the user whose queries are being searched for
     * @return true if the search request of this user is
     */
    boolean existsSearchRequestByUserId(Long userId);

    /**
     * request to the database for deletion search request by user id
     *
     * @param userId id of the user whose queries are being searched for delete
     */
    void deleteByUserId(Long userId);

    /**
     * search query to the database search request bu user
     *
     * @param userId id of the user whose queries are being searched for
     * @return list search requests
     */
    List<SearchRequest> findAllByUserId(Long userId);
}
