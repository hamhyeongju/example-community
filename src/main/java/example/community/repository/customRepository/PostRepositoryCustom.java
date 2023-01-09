package example.community.repository.customRepository;

import example.community.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findAllPageAndSearch(Pageable pageable, PostSearch postSearch);
}
