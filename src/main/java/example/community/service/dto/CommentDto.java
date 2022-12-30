package example.community.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String body;
    private Long member_id;
    private String membername;

    public CommentDto(Long id, String body) {
        this.id = id;
        this.body = body;
    }

    public CommentDto(Long id, String body, String membername) {
        this.id = id;
        this.body = body;
        this.membername = membername;
    }
}
