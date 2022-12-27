package example.community.repository;

import example.community.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    @Query("select h from Heart h where h.post.id = :post_id and h.member.id = :member_id")
    Optional<Heart> findByMemberIdAndPostId(@Param("post_id") Long post_id, @Param("member_id") Long member_id);
}
