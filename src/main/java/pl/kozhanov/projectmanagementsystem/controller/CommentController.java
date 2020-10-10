package pl.kozhanov.projectmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PostMapping("/addNew")
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
    @PostMapping("/delComment")
    @ResponseBody
    public String delComment(
            @RequestParam("commentId") Long commentId) {
        commentService.delComment(commentId);
        return "Comment deleted!";
    }
}
