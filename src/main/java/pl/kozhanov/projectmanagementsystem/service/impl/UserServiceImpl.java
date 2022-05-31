package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.UserDto;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;
import pl.kozhanov.projectmanagementsystem.service.UserService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static pl.kozhanov.projectmanagementsystem.domain.GlobalRole.ROLE_ADMIN;
import static pl.kozhanov.projectmanagementsystem.service.utils.AuthUtils.getLoggedUserName;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final OrikaBeanMapper mapper;

    public UserServiceImpl(final UserRepo userRepo,
                           final PasswordEncoder passwordEncoder,
                           final OrikaBeanMapper mapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        return userRepo.findAllByOrderByUsernameAsc()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(toList());
    }

    @Override
    @Transactional
    public List<UserDto> updateUserRoles(final Integer userId, final UserDto userDto) {
        final User userForUpdate = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        userForUpdate.setGlobalRoles(userDto.getGlobalRoles());

        return userRepo.findAllByOrderByUsernameAsc()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(toList());
    }

    @Override
    @Transactional
    public List<UserDto> addNewUser(final UserDto userDto) {
        final User newUser = mapper.map(userDto, User.class);

        userRepo.save(newUser);

        return userRepo.findAllByOrderByUsernameAsc()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(toList());
    }

    @Override
    @Transactional
    public List<UserDto> deleteUser(final Integer userId) {
        final User userForDelete = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        userRepo.delete(userForDelete);

        return userRepo.findAllByOrderByUsernameAsc()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(toList());
    }

    @Override
    @Transactional
    public void resetUserPassword(final Integer userId) {
        final User userForUpdate = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        userForUpdate.setPassword(passwordEncoder.encode(userForUpdate.getUsername()));
    }

    @Override
    @Transactional
    public UserDto getUserProfile(final Integer userId) {
        final User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (!user.getUsername().equals(getLoggedUserName())) {
            throw new SecurityException("Access not allowed");
        }
        return mapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto changeUserPassword(final UserDto userDto) {
        final User user = userRepo.findById(userDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDto.getUserId()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public boolean isAdmin(final User user) {
        return user.getGlobalRoles().contains(ROLE_ADMIN);
    }
}
