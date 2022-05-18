package pl.kozhanov.projectmanagementsystem.service.validation;

import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewUserValidator implements ConstraintValidator<NewUserConstraint, String> {

    private final UserRepo userRepo;

    public NewUserValidator(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void initialize(NewUserConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

       return userRepo.findAll()
               .stream()
               .noneMatch(user -> username.equals(user.getUsername()));
    }
}
