package example.community.service;

import example.community.domain.Post;
import example.community.repository.PostRepository;
import example.community.service.dto.CommentDto;
import example.community.service.dto.PostDto;
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

    public PostDto findPostAndComment(Long post_id) {
        Post findPost = postRepository.findById(post_id).orElseThrow(IllegalAccessError::new);

        List<CommentDto> commentDtos = findPost.getComments().stream().map(comment -> {
            return new CommentDto(comment.getId(), comment.getBody(), comment.getMember().getName());
        }).collect(Collectors.toList());

        return new PostDto(findPost.getId(), findPost.getTitle(), findPost.getBody(), findPost.getMember().getName(), commentDtos);
    }
}
