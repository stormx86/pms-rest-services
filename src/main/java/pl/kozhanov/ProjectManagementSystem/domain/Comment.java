package pl.kozhanov.ProjectManagementSystem.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment implements Comparable<Comment> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime createdAt;
    private String commentText;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Comment() {
    }

    public Comment(LocalDateTime createdAt, String commentText, Project project, User user) {
        this.createdAt = createdAt;
        this.commentText = commentText;
        this.project = project;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Comment o) {
        if(getCreatedAt() == null || o.getCreatedAt() == null){
            return 0;
        }
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
