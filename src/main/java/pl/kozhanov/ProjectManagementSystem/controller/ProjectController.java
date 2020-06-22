package pl.kozhanov.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.ProjectManagementSystem.service.ProjectService;
import pl.kozhanov.ProjectManagementSystem.service.ProjectStatusService;
import pl.kozhanov.ProjectManagementSystem.service.ProjectView;
import pl.kozhanov.ProjectManagementSystem.service.UserService;

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

    @GetMapping
    public String showProjects(Map<String, Object> model){
        List<ProjectView> projects = projectService.findAllByOrderByCreatedAtDesc();
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
        return "editProject";
    }


    @GetMapping("/newProject")
    public String newProject() {
        return "newProject";
    }


    //usernames for autocomplete
    @GetMapping("/getUserNames")
    @ResponseBody
    public List<String> getUserNames(@RequestParam(value = "term", required = false, defaultValue = "")String term) {
        return userService.findByUsernameLike(term);
    }


    @PostMapping("/add")
    public String addProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
           @RequestParam("pmUser") String pmUser){
        projectService.addProject(title, description, pmUser);

        return "redirect:/projects";
    }


    @PostMapping("save/{projectId}")
    public String saveProject(
            @PathVariable Integer projectId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("pmUser") String pmUser) {
        projectService.saveProject(projectId, title, description, pmUser);

        return "redirect:/projects/{projectId}";
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



