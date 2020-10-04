package pl.kozhanov.projectmanagementsystem.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kozhanov.projectmanagementsystem.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerValidator implements ConstraintValidator<ProjectManagerConstraint, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(ProjectManagerConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String projectManager, ConstraintValidatorContext constraintValidatorContext) {
        if(projectManager.equals("")) return true;
        //get all usernames from DB
        List<String> allUsers = new ArrayList<>();
        userService.findAll().forEach(user-> allUsers.add(user.getUsername()));

        if(allUsers.contains(projectManager)) return true;
        else return false;
    }
}
