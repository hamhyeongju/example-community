package example.community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart extends BaseTimeEntity {

    @Id @GeneratedValue @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    public static Heart createHeart(Post post, Member member) {
        Heart heart = new Heart();
        heart.post = post;
        heart.member = member;

        post.getHearts().add(heart);
        member.getHearts().add(heart);

        return heart;
    }
}
