package pl.kozhanov.projectmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.dto.CommentDto;
import pl.kozhanov.projectmanagementsystem.service.CommentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/comment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> addNewComment(@RequestBody final CommentDto commentForAdding) {
        final Integer newCommentId = commentService.addNewComment(commentForAdding);
        LOGGER.info("Adding new comment");
        return newCommentId != null ? ResponseEntity.ok(newCommentId) : ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/comment")
    public ResponseEntity<Void> deleteNewComment(@RequestParam(value = "commentId") final Long commentId) {
        commentService.deleteComment(commentId);
        LOGGER.info("Deleting comment with id: "+ commentId);
        return ResponseEntity.noContent().build();
    }

}
