package example.community.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String body;
    private String membername;
}
