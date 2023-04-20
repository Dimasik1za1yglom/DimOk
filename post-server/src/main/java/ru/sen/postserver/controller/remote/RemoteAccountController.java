package ru.sen.postserver.controller.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.postserver.controller.remote.api.RemoteAccountApi;
import ru.sen.postserver.dto.remote.ResponseDto;
import ru.sen.postserver.services.ErrorInterceptorService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/posts")
public class RemoteAccountController implements RemoteAccountApi {

    private final ErrorInterceptorService interceptorService;

    @Override
    public ResponseDto deletePosts(Long userId) {
        if (!interceptorService.checkIfDeletePostsByUsersIdSuccessful(userId)) {
            String error = "Не удалось удалить все посты пользователя. Попробуйте позднее";
            log.error("Error on deleting all posts by user id: {}", userId);
            return new ResponseDto(false, error);
        } else {
            log.info("Deleting all posts by user id: {} was successful", userId);
            return new ResponseDto(true, null);
        }
    }
}
