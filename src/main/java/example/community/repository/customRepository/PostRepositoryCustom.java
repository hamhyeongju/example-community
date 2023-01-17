package example.community.repository.customRepository;

import example.community.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @brief JPARepository 확장을 위한 인터페이스
 */
public interface PostRepositoryCustom {

    Page<Post> findAllPageAndSearch(Pageable pageable, PostSearch postSearch);
}
