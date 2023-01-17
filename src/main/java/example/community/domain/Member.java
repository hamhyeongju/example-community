package example.community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Member Entity
 * @relatation Member 1 : N Post, Member 1 : N Comment, Member 1 : N Heart
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

    public static Member createMember(String loginId, String password, String name) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;

        return member;
    }
}
