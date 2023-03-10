package ru.sen.accountserver.services;

import ru.sen.accountserver.entity.AuthorizationData;
import ru.sen.accountserver.forms.AuthorizationDataForm;

public interface AuthorizationDataService {

    boolean dataVerification(AuthorizationDataForm dataForm);

    boolean addData(AuthorizationDataForm dataForm);

    boolean deleteData(Long userId);

    AuthorizationData getData(String email);
}
