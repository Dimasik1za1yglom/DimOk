package ru.sen.searchserver.annotation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.sen.searchserver.annotation.AllFieldsNotBlank;
import ru.sen.searchserver.dto.SearchRequestDto;

public class AllFieldsValidator implements ConstraintValidator<AllFieldsNotBlank, SearchRequestDto> {

    @Override
    public boolean isValid(SearchRequestDto value, ConstraintValidatorContext context) {
        return !(value.getFirstName().isBlank() && value.getLastName().isBlank());
    }
}
