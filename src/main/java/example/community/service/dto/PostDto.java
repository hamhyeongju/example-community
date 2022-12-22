package example.community.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String body;
    private String membername;
    private List<CommentDto> commentDtos = new ArrayList<>();
}
