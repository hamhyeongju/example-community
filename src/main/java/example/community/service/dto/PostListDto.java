package example.community.service.dto;

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
}
