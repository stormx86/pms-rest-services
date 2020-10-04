package pl.kozhanov.projectmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pl.kozhanov.projectmanagementsystem.domain.ProjectModelWrapper;
import pl.kozhanov.projectmanagementsystem.service.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    public static final String LOGGED_USER = "loggedUser";
    public static final String ERROR_ID = "ErrorId";
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
            Map<String, Object> model, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, value = 15) Pageable pageable){
        Page<ProjectViewProjection> projectsPage = projectService.findProjects(projectManagerFilter, createdByFilter, pageable);
        //getting sort property and next sort direction
        Sort sort = projectService.sortManage(pageable.getSort());

        model.put("page", projectsPage);
        model.put("sort", (sort.isSorted())?sort.iterator().next().getProperty():"");
        model.put("nextSortDirection", (sort.isSorted())?sort.iterator().next().getDirection():"ASC");
        model.put(LOGGED_USER, userService.getCurrentLoggedInUsername());
        model.put("url", "/projects");
        return "projects";
    }

    @GetMapping("{projectId}")
    public String openProject(@PathVariable Integer projectId, Model model){
            model.addAttribute("project", projectService.findById(projectId));
            model.addAttribute("statuses", projectStatusService.findAllStatuses());
            model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
            // if currently logged user is not a member of the project --> Access denied
            String currentLoggedInUser = userService.getCurrentLoggedInUsername();
            if(!userService.hasProjectAuthorities(currentLoggedInUser, projectId)) return "project403";
            else return "openProject";
    }

    @GetMapping("edit/{projectId}")
    public String editProject(@PathVariable Integer projectId, Model model) {
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("existingRoles", projectRoleService.findAllRoleNames());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "editProject";
    }


    @GetMapping("/newProject")
    public String newProject(Map<String, Object> model) {
        model.put("existingRoles", projectRoleService.findAllRoleNames());
        model.put(LOGGED_USER, userService.getCurrentLoggedInUsername());
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
     public Map<String, String> addProject(@Valid @ModelAttribute ProjectModelWrapper pmw, BindingResult result) {
        if(result.hasErrors())
        {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            if(RequestContextHolder.getRequestAttributes().getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST) !=null)
                {
                 for(String str: (List<String>)RequestContextHolder.getRequestAttributes().getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST))
                     {
                         errorsMap.put(str, "No such username in the data base");
                     }
                }
            return errorsMap;
        }
        else {
            Map<String, String> map = new HashMap<>();
            map.put("OK", "OK");
            projectService.addProject(pmw.getTitle(), pmw.getDescription(), pmw.getProjectManager(), pmw.getRoles(), pmw.getExistingUsers());
            return map;
        }
    }




    @PostMapping("/save")
    @ResponseBody
    public Map<String, String> saveProject(@Valid @ModelAttribute ProjectModelWrapper pmw, BindingResult result) {
        if(result.hasErrors())
        {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            if(RequestContextHolder.getRequestAttributes().getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST) !=null)
            {
                for(String str: (List<String>)RequestContextHolder.getRequestAttributes().getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST))
                {
                    errorsMap.put(str, "No such username in the data base");
                }
            }
            return errorsMap;
        }
        else {
            Map<String, String> map = new HashMap<>();
            map.put("OK", "OK");
            projectService.saveProject(pmw.getProjectId(), pmw.getTitle(), pmw.getDescription(), pmw.getProjectManager(), pmw.getRoles(), pmw.getExistingUsers());
            return map;
        }
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("delete/{projectId}")
    public String deleteProject(@PathVariable Integer projectId){
        projectService.deleteProject(projectId);
        return "redirect:/projects";
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



