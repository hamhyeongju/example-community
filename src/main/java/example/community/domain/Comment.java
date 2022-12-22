package example.community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue @Column(name = "comment_id")
    private Long id;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "posts_id")
    private Post post;

    public static Comment createComment(String body, Member member, Post post) {
        Comment comment = new Comment();
        comment.body = body;

        comment.post = post;
        post.getComments().add(comment);

        comment.member = member;
        member.getComments().add(comment);

        return comment;
    }
}
