package example.community.service;

import example.community.domain.Comment;
import example.community.domain.Member;
import example.community.domain.Post;
import example.community.repository.CommentRepository;
import example.community.repository.MemberRepository;
import example.community.repository.PostRepository;
import example.community.service.dto.CommentDto;
import example.community.service.dto.EditCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(CommentDto commentDto, Long post_id, Long member_id) {
        Post post = postRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(member_id).orElseThrow(IllegalArgumentException::new);

        Comment comment = Comment.createComment(commentDto.getBody(), member, post);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(Long comment_id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(IllegalArgumentException::new);
        comment.update(commentDto.getBody());
    }

    @Transactional
    public void delete(Long comment_id, Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(IllegalArgumentException::new);
        commentRepository.deleteById(comment_id);
    }

    public EditCommentDto findCommentDto(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(IllegalArgumentException::new);
        return new EditCommentDto(comment.getId(), comment.getBody());
    }

    public Comment findCommentForInterceptor(Long comment_id) {
        return commentRepository.findById(comment_id).orElseThrow(IllegalArgumentException::new);
    }
}
