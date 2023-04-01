package ru.sen.accountserver.annotation.realization;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.sen.accountserver.annotation.Phone;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
    final String PHONE_TEMPLATE = "\\+7\\s?[\\(]{0,1}9[0-9]{2}[\\)]{0,1}\\s?\\d{3}[-]{0,1}\\d{2}[-]{0,1}\\d{2}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(PHONE_TEMPLATE);
    }
}
