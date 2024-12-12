package by.toukachmikhail.taskmanagementsystem.validators.taskstatus;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskStatusValidator.class)
public @interface ValidTaskStatus {
  String message() default "Invalid task status. Only one of: WAITING, PROCESSING, FINISH allowed.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
