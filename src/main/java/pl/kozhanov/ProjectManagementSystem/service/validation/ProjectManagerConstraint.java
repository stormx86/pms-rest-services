package pl.kozhanov.ProjectManagementSystem.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ProjectManagerValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectManagerConstraint {
    String message() default "No such username in the data base";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}