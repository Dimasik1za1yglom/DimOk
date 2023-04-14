package ru.sen.accountserver.annotation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.sen.accountserver.annotation.Phone;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
    final String PHONE_TEMPLATE = "^(\\+7)([0-9]{10})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(PHONE_TEMPLATE);
    }
}
