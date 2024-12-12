package by.toukachmikhail.taskmanagementsystem.validators.taskspriority;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskPriorityValidator.class)
public @interface ValidTaskPriority {
  String message() default "Invalid task priority. Only one of: HIGH, MIDDLE, LOW allowed.";
  Class<?>[]groups() default {};
  Class<? extends Payload>[] payload() default{};

}
