package example.community.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private Long member_id;
    private String membername;
    private Integer heartNum;
    private LocalDateTime createdDate;
    private List<CommentDto> commentDtos = new ArrayList<>();
}
