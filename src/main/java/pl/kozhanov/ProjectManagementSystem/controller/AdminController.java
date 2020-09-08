package pl.kozhanov.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.ProjectManagementSystem.domain.GlobalRole;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectRole;
import pl.kozhanov.ProjectManagementSystem.domain.User;
import pl.kozhanov.ProjectManagementSystem.service.ProjectRoleService;
import pl.kozhanov.ProjectManagementSystem.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    public static final String LOGGED_USER = "loggedUser";
    public static final String PROJECT_ROLES = "projectRoles";
    public static final String ADMIN_PANEL_PROJECT_ROLES_MANAGE = "adminPanelProjectRolesManage";
    public static final String SUCCESS = "success";
    @Autowired
    UserService userService;

    @Autowired
    ProjectRoleService projectRoleService;

    @GetMapping("/adminPanelUserManage")
    public String adminPanelUserManage(Model model, @PageableDefault(value = 10) Pageable pageable) {
        model.addAttribute("page", userService.findAllByOrderByUsernameAsc(pageable));
        model.addAttribute("url", "/admin/adminPanelUserManage");
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "adminPanelUserManage";
    }

    @GetMapping("/adminPanelProjectRolesManage")
    public String adminPanelProjectRolesManage(Model model) {
        model.addAttribute(PROJECT_ROLES, projectRoleService.findAllByOrderByRoleName());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return ADMIN_PANEL_PROJECT_ROLES_MANAGE;
    }


    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result, Model model, @PageableDefault(value = 10) Pageable pageable) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            model.mergeAttributes(errorsMap);
        } else {
            userService.addUser(user);
            model.addAttribute("responseMessage", SUCCESS);
        }
        model.addAttribute("page", userService.findAllByOrderByUsernameAsc(pageable));
        model.addAttribute("url", "/admin/adminPanelUserManage");
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "adminPanelUserManage";
    }

    @GetMapping("editUser/{user}")
    public String editUser(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("globalRoles", GlobalRole.values());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "editUser";
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public String saveUser(@RequestParam("id") Integer userId,
                           @RequestParam("newUsername") String newUsername,
                           @RequestParam("roles[]") String[] roles) {
        return userService.saveUser(userId, newUsername, roles);
    }

    @GetMapping("deleteUser/{user}")
    public String deleteUser(@PathVariable User user) {
        userService.deleteUser(user);
        return "redirect:/admin/adminPanelUserManage";
    }

    @GetMapping("resetUserPassword/{user}")
    public String resetUserPassword(@PathVariable User user, Model model) {
        userService.resetUserPassword(user);
        model.addAttribute("user", user);
        model.addAttribute("globalRoles", GlobalRole.values());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        model.addAttribute("resetResponseMessage", SUCCESS);
        return "editUser";
    }

    @PostMapping("/addNewProjectRole")
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


    @PostMapping("/deleteProjectRole")
    public String deleteProjectRole(@RequestParam ProjectRole projectRole, Model model) {
        projectRoleService.deleteProjectRole(projectRole);
        model.addAttribute(PROJECT_ROLES, projectRoleService.findAllByOrderByRoleName());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        model.addAttribute("responseDeleted", SUCCESS);
        return ADMIN_PANEL_PROJECT_ROLES_MANAGE;
    }


}
