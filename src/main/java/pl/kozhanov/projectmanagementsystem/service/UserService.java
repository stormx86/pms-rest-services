package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.UserDto;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<UserDto> getAllUsers();

    List<UserDto> updateUserRoles(Integer userId, UserDto updatedUser);

    List<UserDto> addNewUser(UserDto userDto);

    List<UserDto> deleteUser(Integer userId);

    void resetUserPassword(Integer userId);

    UserDto getUserProfile(Integer userId);

    UserDto changeUserPassword(UserDto userDto);

    List<String> findByUsernameLike(String term);

    boolean isAdmin();
}
