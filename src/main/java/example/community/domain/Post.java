package example.community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Post Entity
 * @relatation Post N : 1 Member, Post 1 : N Comment, Post 1 : N Heart
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue @Column(name = "post_id")
    private Long id;

    private String title;
    private String body;

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    public static Post createPost(String title, String body, Member member) {
        Post post = new Post();
        post.title = title;
        post.body = body;

        post.member = member;
        member.getPosts().add(post);

        return post;
    }

    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
