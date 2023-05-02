package ru.sen.messagesserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sen.messagesserver.entity.Dialog;

import java.util.List;

/**
 * repository for working with the dialog database
 */
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    /**
     * request to get a list of user dialogs by their id
     *
     * @param userId user id
     * @return list of dialogs of a specific user
     */
    List<Dialog> findAllByUserId(Long userId);

    /**
     * checks how many dialogs there are with the same dialogId
     *
     * @param dialogId dialog Id
     * @return returns the number of dialogs by the same dialog Id
     */
    int countDialogByDialogId(Long dialogId);

    /**
     * sends a request to delete all dialogs of a specific user
     *
     * @param userId user id
     * @return list of dialogs
     */
    List<Dialog> deleteDialogsByUserId(Long userId);

    /**
     * request to get a common dialogId for two dialogs
     *
     * @param createUserId user id of the first dialog
     * @param userId       user id of the second dialog
     * @return dialog id
     */
    @Query(nativeQuery = true,
            value = "select dialog_id from dialogs where user_id = :createUserId or user_id = :userId group by dialog_id having count(*) > 1")
    Long getDialogIdByUsersId(Long createUserId, Long userId);
}
