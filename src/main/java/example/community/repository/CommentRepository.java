package example.community.repository;

import example.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("delete from Comment c where c.post.id = :post_id")
    void deleteByPostId(@Param("post_id") Long post_id);

    @Modifying
    @Query("delete from Comment c where c.member.id = :member_id")
    void deleteByMemberId(@Param("member_id") Long member_id);

    @Modifying
    @Query("delete from Comment c where c.post.id in :post_ids")
    void deleteByPostIds(@Param("post_ids") List<Long> postIds);
}
