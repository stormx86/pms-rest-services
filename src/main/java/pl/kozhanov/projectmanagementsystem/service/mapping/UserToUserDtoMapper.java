package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.UserDto;

@Component
public class UserToUserDtoMapper extends DefaultCustomMapper<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserToUserDtoMapper(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void mapAtoB(User user, UserDto userDto, MappingContext context) {
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setGlobalRoles(user.getGlobalRoles());
        userDto.setPassword("");
    }

    @Override
    public void mapBtoA(UserDto userDto, User user, MappingContext context) {
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getUsername()));
        user.setActive(true);
        user.setGlobalRoles(userDto.getGlobalRoles());
    }
}
