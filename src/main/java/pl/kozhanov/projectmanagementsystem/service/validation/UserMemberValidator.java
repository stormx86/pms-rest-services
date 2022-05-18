package pl.kozhanov.projectmanagementsystem.service.validation;

import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.UserProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMemberValidator implements ConstraintValidator<UserMemberConstraint, Set<UserProjectRoleDto>> {

    private final UserRepo userRepo;

    public UserMemberValidator(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void initialize(UserMemberConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set<UserProjectRoleDto> userMemberRoles, ConstraintValidatorContext ctx) {

        final List<String> allDataBaseUsernames = userRepo.findAll().stream().map(User::getUsername).collect(Collectors.toList());

        final List<String> inputUserMemberNames = userMemberRoles.stream().map(UserProjectRoleDto::getUserName).collect(Collectors.toList());

        return allDataBaseUsernames.containsAll(inputUserMemberNames);
    }
}