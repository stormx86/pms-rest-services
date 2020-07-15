package pl.kozhanov.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("projectRoles", projectRoleService.findAllByOrderByRoleName());
        model.addAttribute("loggedUser", userService.getCurrentLoggedInUsername());
        return "adminPanelProjectRolesManage";
    }


    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result, Model model)
    {
        if(result.hasErrors())
            {
                Map<String, String> errorsMap = ControllerUtils.getErrors(result);
                model.mergeAttributes(errorsMap);
            }
        else {
            userService.addUser(user);
        }
        model.addAttribute("users", userService.findAll());
        model.addAttribute("loggedUser", userService.getCurrentLoggedInUsername());
        return "adminPanelUserManage";
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
    public String addNewProjectRole(@Valid ProjectRole projectRole, BindingResult result, Model model){
        if(result.hasErrors())
        {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            model.mergeAttributes(errorsMap);
        }
        else {
            projectRoleService.addNewProjectRole(projectRole);
        }
        model.addAttribute("projectRoles", projectRoleService.findAllByOrderByRoleName());
        model.addAttribute("loggedUser", userService.getCurrentLoggedInUsername());
        return "adminPanelProjectRolesManage";
    }


    @GetMapping("deleteProjectRole/{projectRole}")
    public String deleteProjectRole(@PathVariable ProjectRole projectRole){
        projectRoleService.deleteProjectRole(projectRole);
        return "redirect:/admin/adminPanelProjectRolesManage";
    }


}
