package example.community.repository;

import example.community.domain.Post;
import example.community.repository.customRepository.PostRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query(value = "select p from Post p left join fetch p.member",
    countQuery = "select count (p) from Post p")
    Page<Post> findPostList(Pageable pageable);

    @Query("select p from Post p where p.member.id = :member_id")
    List<Post> findAllByMemberId(@Param("member_id") Long member_id);

    @Modifying
    @Query("delete from Post p where p.id = :post_id")
    void deleteByPostId(@Param("post_id") Long post_id);

    @Modifying
    @Query("delete from Post p where p.id in :post_ids")
    void deleteByPostIds(@Param("post_ids") List<Long> postIds);
}
