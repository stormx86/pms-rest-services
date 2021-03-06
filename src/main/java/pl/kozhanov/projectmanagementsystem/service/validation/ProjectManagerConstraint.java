package pl.kozhanov.projectmanagementsystem.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ProjectManagerValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectManagerConstraint {
    String message() default "User not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}