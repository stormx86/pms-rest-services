package pl.kozhanov.projectmanagementsystem.domain;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Instant createdAt;
    private String commentText;
    private String createdAtView;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Comment() {
    }

    public Comment(Instant createdAt, String commentText, Project project, User user) {
        this.createdAt = createdAt;
        this.commentText = commentText;
        this.project = project;
        this.user = user;
        this.createdAtView = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
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

    public String getCreatedAtView() { return createdAtView; }

    public void setCreatedAtView() {this.createdAtView = createdAtView;}

}
