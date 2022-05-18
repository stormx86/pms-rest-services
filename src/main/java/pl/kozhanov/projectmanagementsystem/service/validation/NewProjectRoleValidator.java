package pl.kozhanov.projectmanagementsystem.service.validation;

import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewProjectRoleValidator implements ConstraintValidator<NewProjectRoleConstraint, String> {

    private final ProjectRoleRepo projectRoleRepo;

    public NewProjectRoleValidator(final ProjectRoleRepo projectRoleRepo) {
        this.projectRoleRepo = projectRoleRepo;
    }

    @Override
    public void initialize(NewProjectRoleConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roleName, ConstraintValidatorContext constraintValidatorContext) {
        return projectRoleRepo.findAll()
                .stream()
                .noneMatch(role -> roleName.equals(role.getRoleName()));
    }
}
