package pl.kozhanov.projectmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.dto.UserDto;
import pl.kozhanov.projectmanagementsystem.service.UserService;
import pl.kozhanov.projectmanagementsystem.service.validation.groups.ValidationGroups;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/profile/{id}", produces = "application/json")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("id") Integer userId) {

        final UserDto userProfile = userService.getUserProfile(userId);
        LOGGER.info("Retrieving user profile for id: " + userId);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping(value = "/change/password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> changeUserPassword(@RequestBody @Validated(value= ValidationGroups.ChangeUserPassword.class) final UserDto userDto) {

        final UserDto userProfile = userService.changeUserPassword(userDto);
        LOGGER.info("Changing password for user with id: " + userDto.getUserId());
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        final List<UserDto> userList = userService.getAllUsers();
        LOGGER.info("Retrieving all users.");
        return isEmpty(userList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(userList);
    }
}
