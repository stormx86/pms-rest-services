package pl.kozhanov.projectmanagementsystem.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.service.impl.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserMemberValidator implements ConstraintValidator<UserMemberConstraint, List<String>> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UserMemberConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> userMembers, ConstraintValidatorContext ctx) {
        if (userMembers == null) return true;
        //get all usernames from DB
        List<String> allUsers = new ArrayList<>();
        for(User user: userService.findAll())
        {
            allUsers.add(user.getUsername());
        }

        List<String> userMembersNames = new ArrayList<>();
        List<String> userMembersInputsId = new ArrayList<>();
        for(String str:userMembers)
            {
                String[] split = str.split("!");
                userMembersNames.add(split[0]);
                if(!allUsers.contains(split[0])) userMembersInputsId.add(split[1]);
            }

        if(allUsers.containsAll(userMembersNames)) return true;
        else
        {
            RequestContextHolder.getRequestAttributes().setAttribute("ErrorId", userMembersInputsId, RequestAttributes.SCOPE_REQUEST);
            return false;
        }
    }
}
