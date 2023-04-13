package ru.sen.searchserver.services;

public interface ErrorInterceptorService {

    boolean checkIfDeletingSearchRequestSuccessful(Long userId);
}
