package example.community.service;

import example.community.domain.Member;
import example.community.domain.Post;
import example.community.repository.CommentRepository;
import example.community.repository.HeartRepository;
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
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    public List<PostListDto> findList() {
        List<Post> find = postRepository.findPostList();
        return find.stream().map(post -> {
            return new PostListDto(post.getId(), post.getTitle(), post.getMember().getName(),
                    post.getHearts().size(), post.getComments().size(), post.getCreatedDate());
        }).collect(Collectors.toList());
    }

    public PostDto findPostAndComment(Long post_id) {
        Post findPost = postRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);

        List<CommentDto> commentDtos = findPost.getComments().stream().map(comment -> {
            return new CommentDto(comment.getId(), comment.getBody(), comment.getMember().getId(), comment.getMember().getName());
        }).collect(Collectors.toList());

        return new PostDto(findPost.getId(), findPost.getTitle(), findPost.getBody(), findPost.getMember().getId(), findPost.getMember().getName(), findPost.getHearts().size(), findPost.getCreatedDate(), commentDtos);
    }

    public WritePostDto findWritePostDto(Long post_id) {
        Post findPost = postRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        return new WritePostDto(findPost.getId(), findPost.getTitle(), findPost.getBody());
    }

    @Transactional
    public Long createPost(WritePostDto writePostDto, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Post post = Post.createPost(writePostDto.getTitle(), writePostDto.getBody(), findMember);

        return postRepository.save(post).getId();
    }

    @Transactional
    public void updatePost(Long post_id, WritePostDto writePostDto) {
        Post post = postRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        post.update(writePostDto.getTitle(), writePostDto.getBody());
    }

    @Transactional
    public void delete(Long post_id) {
        heartRepository.deleteByPostId(post_id);
        commentRepository.deleteByPostId(post_id);
        postRepository.deleteByPostId(post_id);
    }

    @Transactional
    public void deleteByMemberId(Long member_id) {
        List<Post> findPosts = postRepository.findAllByMemberId(member_id);
        List<Long> postIds = findPosts.stream().map(post -> {return post.getId();}).collect(Collectors.toList());

        heartRepository.deleteByPostIds(postIds);
        commentRepository.deleteByPostIds(postIds);
        postRepository.deleteByPostIds(postIds);
    }

    public Post findPostForInterceptor(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }
}
