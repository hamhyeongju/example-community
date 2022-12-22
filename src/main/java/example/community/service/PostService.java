package example.community.service;

import example.community.domain.Post;
import example.community.repository.PostRepository;
import example.community.service.dto.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostListDto> findList() {
        List<Post> find = postRepository.findPostList();
        return find.stream().map(post -> {
            return new PostListDto(post.getId(), post.getTitle(), post.getMember().getName(),
                    post.getHeartNum(), post.getCommentNum());
        }).collect(Collectors.toList());
    }
}
