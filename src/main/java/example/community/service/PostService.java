package example.community.service;

import example.community.domain.Member;
import example.community.domain.Post;
import example.community.repository.MemberRepository;
import example.community.repository.PostRepository;
import example.community.service.dto.CommentDto;
import example.community.service.dto.PostDto;
import example.community.service.dto.PostListDto;
import example.community.service.dto.WritePostDto;
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
    private final MemberRepository memberRepository;

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

    public WritePostDto findWritePostDto(Long post_id) {
        Post findPost = postRepository.findById(post_id).orElseThrow(IllegalAccessError::new);
        return new WritePostDto(findPost.getTitle(), findPost.getBody());
    }

    @Transactional
    public Long createPost(WritePostDto writePostDto, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalAccessError::new);

        Post post = Post.createPost(writePostDto.getTitle(), writePostDto.getBody(), findMember);

        return postRepository.save(post).getId();
    }

    @Transactional
    public void updatePost(Long post_id, WritePostDto writePostDto) {
        Post post = postRepository.findById(post_id).orElseThrow(IllegalAccessError::new);
        post.update(writePostDto.getTitle(), writePostDto.getBody());
    }

    @Transactional
    public void delete(Long post_id) {
        postRepository.deleteById(post_id);
    }
}
