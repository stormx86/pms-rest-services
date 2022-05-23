package pl.kozhanov.projectmanagementsystem.service.validation;

import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsernameConstraint, String> {

    private final UserRepo userRepo;

    public ValidUsernameValidator(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void initialize(ValidUsernameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {

        return userRepo.findAll().stream().anyMatch(user-> userName.equals(user.getUsername()));
    }
}
