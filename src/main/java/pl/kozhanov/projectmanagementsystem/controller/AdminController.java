package pl.kozhanov.projectmanagementsystem.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.domain.GlobalRole;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;
import pl.kozhanov.projectmanagementsystem.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private static final String LOGGED_USER = "loggedUser";
    private static final String PROJECT_ROLES = "projectRoles";
    private static final String ADMIN_PANEL_PROJECT_ROLES_MANAGE = "adminPanelProjectRolesManage";
    private static final String SUCCESS = "success";

    private final UserService userService;
    private final ProjectRoleService projectRoleService;

    public AdminController(final UserService userService, final ProjectRoleService projectRoleService) {
        this.userService = userService;
        this.projectRoleService = projectRoleService;
    }

    @GetMapping("/users")
    public String adminPanelUserManage(Model model, @PageableDefault(value = 10) Pageable pageable) {
        model.addAttribute("page", userService.findAllByOrderByUsernameAsc(pageable));
        model.addAttribute("url", "/admin/users");
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "adminPanelUserManage";
    }

    @GetMapping("/roles")
    public String adminPanelProjectRolesManage(Model model) {
        model.addAttribute(PROJECT_ROLES, projectRoleService.findAllByOrderByRoleName());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return ADMIN_PANEL_PROJECT_ROLES_MANAGE;
    }

    @PostMapping("/users/add")
    public String addUser(@Valid User user, BindingResult result, Model model,
                          @PageableDefault(value = 10) Pageable pageable) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            model.mergeAttributes(errorsMap);
        } else {
            userService.addUser(user);
            model.addAttribute("responseMessage", SUCCESS);
        }
        model.addAttribute("page", userService.findAllByOrderByUsernameAsc(pageable));
        model.addAttribute("url", "/admin/users");
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "adminPanelUserManage";
    }

    @GetMapping("/users/{user}")
    public String editUser(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("globalRoles", GlobalRole.values());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "editUser";
    }

    @PutMapping("/users/{user}")
    @ResponseBody
    public String saveUser(@RequestParam("id") Integer userId,
                           @RequestParam("newUsername") String newUsername,
                           @RequestParam("roles[]") String[] roles) {
        return userService.saveUser(userId, newUsername, roles);
    }

    @DeleteMapping("/users/{user}")
    public String deleteUser(@PathVariable User user) {
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }

    @PutMapping("users/{user}/reset-password")
    public String resetUserPassword(@PathVariable User user, Model model) {
        userService.resetUserPassword(user);
        model.addAttribute("user", user);
        model.addAttribute("globalRoles", GlobalRole.values());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        model.addAttribute("resetResponseMessage", SUCCESS);
        return "editUser";
    }

    @PostMapping("/roles/add")
    public String addNewProjectRole(@Valid ProjectRole projectRole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            model.mergeAttributes(errorsMap);
        } else {
            projectRoleService.addNewProjectRole(projectRole);
            model.addAttribute("responseCreated", SUCCESS);
        }
        model.addAttribute(PROJECT_ROLES, projectRoleService.findAllByOrderByRoleName());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return ADMIN_PANEL_PROJECT_ROLES_MANAGE;
    }

    @DeleteMapping("/roles/{role}")
    public String deleteProjectRole(@PathVariable ProjectRole role, Model model) {
        projectRoleService.deleteProjectRole(role);
        model.addAttribute(PROJECT_ROLES, projectRoleService.findAllByOrderByRoleName());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        model.addAttribute("responseDeleted", SUCCESS);
        return ADMIN_PANEL_PROJECT_ROLES_MANAGE;
    }
}
