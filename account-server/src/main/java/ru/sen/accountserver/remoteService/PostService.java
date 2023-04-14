package ru.sen.accountserver.remoteService;

import ru.sen.accountserver.dto.remote.ResponseDto;

public interface PostService {

    ResponseDto deletePosts(Long userId);
}
