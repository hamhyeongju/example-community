package example.community.repository;

import example.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p left join fetch p.member")
    List<Post> findPostList();

    @Modifying
    @Query("delete from Post p where p.id = :post_id")
    void deleteByPostId(@Param("post_id") Long post_id);

    @Modifying
    @Query("delete from Post p where p.member.id = :member_id")
    void deleteByMemberId(@Param("member_id") Long member_id);
}
