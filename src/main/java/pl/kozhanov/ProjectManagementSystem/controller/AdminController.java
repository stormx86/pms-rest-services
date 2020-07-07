package pl.kozhanov.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.ProjectManagementSystem.domain.GlobalRole;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectRole;
import pl.kozhanov.ProjectManagementSystem.domain.User;
import pl.kozhanov.ProjectManagementSystem.service.ProjectRoleService;
import pl.kozhanov.ProjectManagementSystem.service.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    ProjectRoleService projectRoleService;

    @GetMapping("/adminPanelUserManage")
    public String adminPanelUserManage(Model model)
    {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("loggedUser", userService.getCurrentLoggedInUsername());
       return "adminPanelUserManage";
    }

    @GetMapping("/adminPanelProjectRolesManage")
    public String adminPanelProjectRolesManage(Model model)
    {
        model.addAttribute("loggedUser", userService.getCurrentLoggedInUsername());
        model.addAttribute("projectRoles", projectRoleService.findAllByOrderByRoleName());
        return "adminPanelProjectRolesManage";
    }

    @PostMapping("/addUser")
    @ResponseBody
    public String addUser(@RequestParam String newUsername){
        return userService.addUser(newUsername);
    }

    @GetMapping("editUser/{user}")
    public String editUser(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("globalRoles", GlobalRole.values());
        model.addAttribute("loggedUser", userService.getCurrentLoggedInUsername());
        return "editUser";
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public String saveUser(@RequestParam("id") Integer userId,
                           @RequestParam("newUsername") String newUsername,
                           @RequestParam("roles[]") String[] roles){
        return userService.saveUser(userId, newUsername, roles);
    }

    @GetMapping("deleteUser/{user}")
    public String deleteUser(@PathVariable User user){
        userService.deleteUser(user);
        return "redirect:/admin/adminPanelUserManage";
    }

    @PostMapping("/addNewProjectRole")
    @ResponseBody
    public String addNewProjectRole(@RequestParam String newProjectRole){
        return projectRoleService.addNewProjectRole(newProjectRole);
    }


    @GetMapping("deleteProjectRole/{projectRole}")
    public String deleteProjectRole(@PathVariable ProjectRole projectRole){
        projectRoleService.deleteProjectRole(projectRole);
        return "redirect:/admin/adminPanelProjectRolesManage";
    }


}
