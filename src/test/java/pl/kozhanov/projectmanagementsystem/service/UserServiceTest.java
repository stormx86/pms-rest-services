package pl.kozhanov.projectmanagementsystem.service;
import org.hamcrest.CoreMatchers;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kozhanov.projectmanagementsystem.domain.GlobalRole;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserServiceTest {
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private UserRepo userRepo = mock(UserRepo.class);
    private UserService userService = new UserService(userRepo, passwordEncoder);

    @Test
    void addUserTest() {
        User user = new User();
        user.setUsername("Nick");
        Mockito.when(passwordEncoder.encode(user.getUsername())).thenReturn(user.getUsername());
        userService.addUser(user);
        assertTrue(user.isActive());
        assertTrue(CoreMatchers.is(user.getGlobalRoles()).matches(Collections.singleton(GlobalRole.USER)));
        assertEquals("Nick", user.getPassword());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void saveUserTest() {
        User user = new User();
        user.setUsername("Alex");
        user.setGlobalRoles(new HashSet<>());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        int userId = 1;
        String newUsername = "Nick";
        String[] roles = {"ADMIN"};
        Mockito.when(userRepo.findAll()).thenReturn(userList);
        Mockito.when(userRepo.getById(userId)).thenReturn(user);
        userService.saveUser(userId, newUsername, roles);
        assertEquals("Nick", user.getUsername());
        assertTrue(CoreMatchers.is(user.getGlobalRoles()).matches(Collections.singleton(GlobalRole.ADMIN)));
    }

    @Test
    void deleteUser_shouldCallDbOnce() {
        User user = new User();
        userService.deleteUser(user);
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    @Test
    void changeUserPassword_ifPasswordEncodeCorrect_shouldReturn123() {
        User user = new User();
        String username = "Nick";
        String password = "123";
        Mockito.when(userRepo.findByUsername(username)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(password)).thenReturn("321");
        userService.changeUserPassword(username, password);
        assertEquals("321", user.getPassword());
    }

    @Test
    void resetUserPassword_ifUsernameIsNick_passwordShoulBeNick() {
        User user = new User();
        user.setUsername("Nick");
        Mockito.when(passwordEncoder.encode(user.getUsername())).thenReturn(user.getUsername());
        userService.resetUserPassword(user);
        assertEquals("Nick", user.getPassword());
    }
}