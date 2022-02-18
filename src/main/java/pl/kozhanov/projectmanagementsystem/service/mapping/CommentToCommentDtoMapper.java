package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.dto.CommentDto;

@Component
public class CommentToCommentDtoMapper extends DefaultCustomMapper<Comment, CommentDto> {

    @Override
    public void mapAtoB(Comment comment, CommentDto commentDto, MappingContext context) {
        commentDto.setCommentId(comment.getId());
        commentDto.setProjectId(comment.getProject().getId());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setUsername(comment.getUser().getUsername());
    }
}
