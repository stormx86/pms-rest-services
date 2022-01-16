package pl.kozhanov.projectmanagementsystem.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;
import pl.kozhanov.projectmanagementsystem.service.impl.ProjectRoleServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewProjectRoleValidator implements ConstraintValidator<NewProjectRoleConstraint, String> {

    @Autowired
    ProjectRoleService projectRoleService;

    @Override
    public void initialize(NewProjectRoleConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roleName, ConstraintValidatorContext constraintValidatorContext) {
        return !projectRoleService.findAllRoleNames().contains(roleName);
    }
}
