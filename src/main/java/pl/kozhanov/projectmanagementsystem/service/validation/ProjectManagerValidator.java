package pl.kozhanov.projectmanagementsystem.service.validation;

import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjectManagerValidator implements ConstraintValidator<ProjectManagerConstraint, String> {

    private final UserRepo userRepo;

    public ProjectManagerValidator(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void initialize(ProjectManagerConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String projectManager, ConstraintValidatorContext constraintValidatorContext) {

        return userRepo.findAll().stream().anyMatch(user-> projectManager.equals(user.getUsername()));
    }
}
