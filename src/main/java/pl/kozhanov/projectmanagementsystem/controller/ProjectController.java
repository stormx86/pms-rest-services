package pl.kozhanov.projectmanagementsystem.controller;

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

    private static final String LOGGED_USER = "loggedUser";
    private static final String ERROR_ID = "ErrorId";

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectStatusService projectStatusService;
    private final ProjectRoleService projectRoleService;

    public ProjectController(final ProjectService projectService,
                             final UserService userService,
                             final ProjectStatusService projectStatusService,
                             final ProjectRoleService projectRoleService) {
        this.projectService = projectService;
        this.userService = userService;
        this.projectStatusService = projectStatusService;
        this.projectRoleService = projectRoleService;
    }

    @GetMapping
    public String showProjects(
            @RequestParam(defaultValue = "") String projectManagerFilter,
            @RequestParam(defaultValue = "") String createdByFilter,
            Map<String, Object> model,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, value = 15) Pageable pageable) {
        Page<ProjectViewProjection> projectsPage = projectService.findProjects(
                projectManagerFilter, createdByFilter, pageable);
        //getting sort property and next sort direction
        Sort sort = projectService.sortManage(pageable.getSort());
        model.put("page", projectsPage);
        model.put("sort", (sort.isSorted()) ? sort.iterator().next().getProperty() : "");
        model.put("nextSortDirection", (sort.isSorted()) ? sort.iterator().next().getDirection() : "ASC");
        model.put(LOGGED_USER, userService.getCurrentLoggedInUsername());
        model.put("url", "/projects");
        return "projects";
    }

    @GetMapping("{project}")
    public String openProject(@PathVariable Integer project, Model model) {
        model.addAttribute("project", projectService.findById(project));
        model.addAttribute("statuses", projectStatusService.findAllStatuses());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        // if currently logged user is not a member of the project --> Access denied
        String currentLoggedInUser = userService.getCurrentLoggedInUsername();
        if (!userService.hasProjectAuthorities(currentLoggedInUser, project)) return "project403";
        else return "openProject";
    }

    @GetMapping("/{project}/edit")
    public String editProject(@PathVariable Integer project, Model model) {
        model.addAttribute("project", projectService.findById(project));
        model.addAttribute("existingRoles", projectRoleService.findAllRoleNames());
        model.addAttribute(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "editProject";
    }

    @GetMapping("/new")
    public String newProject(Map<String, Object> model) {
        model.put("existingRoles", projectRoleService.findAllRoleNames());
        model.put(LOGGED_USER, userService.getCurrentLoggedInUsername());
        return "newProject";
    }

    //usernames for autocomplete
    @GetMapping("/get-usernames")
    @ResponseBody
    public List<String> getUserNames(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return userService.findByUsernameLike(term);
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, String> addProject(@Valid @ModelAttribute ProjectModelWrapper pmw, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            if (RequestContextHolder.getRequestAttributes().getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST) != null) {
                for (String str : (List<String>) RequestContextHolder.getRequestAttributes()
                        .getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST)) {
                    errorsMap.put(str, "No such username in the data base");
                }
            }
            return errorsMap;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("OK", "OK");
            projectService.addProject(pmw.getTitle(), pmw.getDescription(), pmw.getProjectManager(), pmw.getRoles(),
                    pmw.getExistingUsers());
            return map;
        }
    }

    @PutMapping("/{project}")
    @ResponseBody
    public Map<String, String> saveProject(@Valid @ModelAttribute ProjectModelWrapper pmw, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(result);
            if (RequestContextHolder.getRequestAttributes().getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST) != null) {
                for (String str : (List<String>) RequestContextHolder.getRequestAttributes()
                        .getAttribute(ERROR_ID, RequestAttributes.SCOPE_REQUEST)) {
                    errorsMap.put(str, "No such username in the data base");
                }
            }
            return errorsMap;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("OK", "OK");
            projectService.saveProject(pmw.getProjectId(), pmw.getTitle(), pmw.getDescription(),
                    pmw.getProjectManager(), pmw.getRoles(), pmw.getExistingUsers());
            return map;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{project}")
    public String deleteProject(@PathVariable Integer project) {
        projectService.deleteProject(project);
        return "redirect:/projects";
    }

    @PutMapping("/{project}/change-status")
    @ResponseBody
    public String changeProjectStatus(
            @RequestParam("id") Integer projectId,
            @RequestParam("status") String status) {
        projectService.changeProjectStatus(projectId, status);
        return "Status changed!";
    }
}



