package ru.sen.searchserver.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.sen.searchserver.annotation.validators.AllFieldsValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllFieldsValidator.class)
public @interface AllFieldsNotBlank {

    String message() default "Сразу все поля не могут быть пустыми или состоять из пробелов";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
