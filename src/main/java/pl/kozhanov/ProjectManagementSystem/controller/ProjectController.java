package pl.kozhanov.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.ProjectManagementSystem.service.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectStatusService projectStatusService;

    @Autowired
    ProjectRoleService projectRoleService;

    @GetMapping
    public String showProjects(
            @RequestParam(defaultValue = "") String projectManagerFilter,
            @RequestParam(defaultValue = "") String createdByFilter,
            Map<String, Object> model){
        List<ProjectMainProjection> projects = projectService.findProjects(projectManagerFilter, createdByFilter);
        model.put("projects", projects);
        return "projects";
    }

    @GetMapping("{projectId}")
    public String openProject(@PathVariable Integer projectId, Model model){
            model.addAttribute("project", projectService.findById(projectId));
            model.addAttribute("statuses", projectStatusService.findAllStatuses());
        return "openProject";
    }

    @GetMapping("edit/{projectId}")
    public String editProject(@PathVariable Integer projectId, Model model) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("existingRoles", projectRoleService.findAllRoles());
        return "editProject";
    }


    @GetMapping("/newProject")
    public String newProject(Map<String, Object> model) {
        model.put("existingRoles", projectRoleService.findAllRoles());
        return "newProject";
    }


    //usernames for autocomplete
    @GetMapping("/getUserNames")
    @ResponseBody
    public List<String> getUserNames(@RequestParam(value = "term", required = false, defaultValue = "")String term) {
        return userService.findByUsernameLike(term);
    }


    @PostMapping("/add")
    @ResponseBody
    public String addProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("roles[]") String[] roles,
            @RequestParam("users[]") String[] users) {
        projectService.addProject(title, description, roles, users);
        return "OK";
    }


    @PostMapping("/save")
    @ResponseBody
    public String saveProject(
            @RequestParam("id") Integer projectId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("roles[]") String[] roles,
            @RequestParam("users[]") String[] users) {
        projectService.saveProject(projectId, title, description, roles, users);
        return "Project saved!";
    }

    @PostMapping("changeStatus")
    @ResponseBody
    public String changeProjectStatus(
            @RequestParam("id") Integer projectId,
            @RequestParam("status") String status) {
        projectService.changeProjectStatus(projectId, status);
        return "Status changed!";
    }



}



