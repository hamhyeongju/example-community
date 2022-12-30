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

    public CommentDto(Long id, String body) {
        this.id = id;
        this.body = body;
    }
}
