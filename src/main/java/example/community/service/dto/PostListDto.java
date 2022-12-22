package example.community.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PostListDto {

    private Long id; // postId
    private String title;
    private String membername;
    private int likeNum;
    private int commentNum;
}
