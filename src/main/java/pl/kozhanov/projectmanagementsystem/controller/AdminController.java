package pl.kozhanov.projectmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.dto.UserDto;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;
import pl.kozhanov.projectmanagementsystem.service.UserService;
import pl.kozhanov.projectmanagementsystem.service.validation.groups.ValidationGroups;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final ProjectRoleService projectRoleService;

    public AdminController(final UserService userService,
                           final ProjectRoleService projectRoleService) {
        this.userService = userService;
        this.projectRoleService = projectRoleService;
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        final List<UserDto> userList = userService.getAllUsers();
        LOGGER.info("Retrieving all users.");
        return isEmpty(userList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(userList);
    }

    @PutMapping(value = "/users/edit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<UserDto>> updateUserGlobalRoles(@RequestParam(value = "userId") final Integer userId,
                                                         @RequestBody final UserDto userDto) {

        final List<UserDto> updatedUserList = userService.updateUserRoles(userId, userDto);
        LOGGER.info("Updating roles for user with id: " + userId);
        return isEmpty(updatedUserList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(updatedUserList);
    }

    @PostMapping(value = "/users/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<UserDto>> addNewUser(@RequestBody @Validated(value= ValidationGroups.NewUser.class) final UserDto userDto) {

        final List<UserDto> updatedUserList = userService.addNewUser(userDto);
        LOGGER.info("Adding new user with name: " + userDto.getUsername());
        return isEmpty(updatedUserList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(updatedUserList);
    }

    @DeleteMapping(value = "/users/delete", produces = "application/json")
    public ResponseEntity<List<UserDto>> deleteUser(@RequestParam(value = "userId") final Integer userId) {

        final List<UserDto> updatedUserList = userService.deleteUser(userId);
        LOGGER.info("Deleting user with id: " + userId);
        return isEmpty(updatedUserList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(updatedUserList);
    }

    @PutMapping(value = "/users/reset", produces = "application/json")
    public ResponseEntity<Void> resetUserPassword(@RequestParam(value = "userId") final Integer userId) {

        userService.resetUserPassword(userId);
        LOGGER.info("Resetting password for user with id: " + userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/roles/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ProjectRoleDto>> addNewProjectRole(
            @RequestBody @Validated(value= ValidationGroups.NewProjectRole.class) final ProjectRoleDto projectRoleDto) {

        final List<ProjectRoleDto> updatedProjectRolesList = projectRoleService.addNewProjectRole(projectRoleDto);
        LOGGER.info("Adding new project role with name: " + projectRoleDto.getRoleName());
        return isEmpty(updatedProjectRolesList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(updatedProjectRolesList);
    }

    @DeleteMapping(value = "/roles/delete", produces = "application/json")
    public ResponseEntity<List<ProjectRoleDto>> deleteProjectRole(@RequestParam(value = "projectRoleId") final Integer projectRoleId) {

        final List<ProjectRoleDto> updatedProjectRoleList = projectRoleService.deleteProjectRole(projectRoleId);
        LOGGER.info("Deleting project role with id: " + projectRoleId);
        return isEmpty(updatedProjectRoleList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(updatedProjectRoleList);
    }
}
