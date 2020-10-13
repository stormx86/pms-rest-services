package pl.kozhanov.projectmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.service.CommentService;
import pl.kozhanov.projectmanagementsystem.service.ProjectService;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private ProjectService projectService;
    private CommentService commentService;

    @Autowired
    public CommentController(ProjectService projectService, CommentService commentService) {
        this.projectService = projectService;
        this.commentService = commentService;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addNewComment(
            @RequestParam("id") Integer projectId,
            @RequestParam("commentText") String commentText) {
        if (commentText.equals("")) return "Empty comment";
        else {
            projectService.addNewComment(projectId, commentText);
            return "Comment added!";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{comment}")
    @ResponseBody
    public String delComment(@PathVariable Long comment) {
        commentService.delComment(comment);
        return "Comment deleted!";
    }
}
