package ru.sen.searchserver.controller.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sen.searchserver.controller.remote.api.RemoteAccountApi;
import ru.sen.searchserver.dto.remote.ResponseDto;
import ru.sen.searchserver.services.ErrorInterceptorService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/searchRequest")
public class RemoteAccountController implements RemoteAccountApi {

    private final ErrorInterceptorService interceptorService;

    @Override
    public ResponseDto deleteSearchRequests(Long userId) {
        if (!interceptorService.checkIfDeletingSearchRequestSuccessful(userId)) {
            String error = "Не удалось удалить историю запросов. Попробуйте позднее";
            log.error("Error on deleting a search request under id: {}", userId);
            return new ResponseDto(false, error);
        } else {
            log.info("Deleting the search request was successful");
            return new ResponseDto(true, null);
        }
    }
}
