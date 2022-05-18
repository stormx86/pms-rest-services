package pl.kozhanov.projectmanagementsystem.service.validation;

import pl.kozhanov.projectmanagementsystem.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangeUserPasswordValidator implements ConstraintValidator<ChangeUserPasswordConstraint, UserDto> {

    @Override
    public void initialize(ChangeUserPasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        return userDto.getPassword().equals(userDto.getRepeatPassword());
    }
}
