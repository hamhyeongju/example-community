package example.community.service.dto;

import example.community.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class PostListDto {

    private Long id; // postId
    private String title;
    private String membername;
    private int HeartNum;
    private int commentNum;
    private LocalDateTime createdDate;

    public PostListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.membername = post.getMember().getName();
        this.HeartNum = post.getHearts().size();
        this.commentNum = post.getComments().size();
        this.createdDate = post.getCreatedDate();
    }
}
