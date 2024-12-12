package by.toukachmikhail.taskmanagementsystem.validators.digits;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DigitValidator.class)
public @interface ValidIsDigit {

  String message() default "Invalid id. Only digits are allowed.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
