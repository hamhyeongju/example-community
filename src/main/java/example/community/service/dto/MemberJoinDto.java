package example.community.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberJoinDto {
    private String loginId;
    private String password;
    private String checkPassword;
    private String name;
}
