package pl.kozhanov.projectmanagementsystem.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChangeUserPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeUserPasswordConstraint {
    String message() default "Password fields must be equal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}