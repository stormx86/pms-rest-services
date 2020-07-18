package pl.kozhanov.ProjectManagementSystem.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewProjectRoleValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NewProjectRoleConstraint {
    String message() default "Project Role already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}