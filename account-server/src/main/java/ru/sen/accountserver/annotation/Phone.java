package ru.sen.accountserver.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.sen.accountserver.annotation.validators.PhoneConstraintValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneConstraintValidator.class)
public @interface Phone {

    String message() default "Номер телефона должен быть в формате +79XXXXXXXXX";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
