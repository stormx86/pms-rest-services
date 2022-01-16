package pl.kozhanov.projectmanagementsystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.service.CommentService;
import pl.kozhanov.projectmanagementsystem.service.ProjectService;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final ProjectService projectService;
    private final CommentService commentService;

    public CommentController(final ProjectService projectService, final CommentService commentService) {
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
        commentService.deleteComment(comment);
        return "Comment deleted!";
    }
}
