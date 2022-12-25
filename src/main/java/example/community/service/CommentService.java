package example.community.service;

import example.community.domain.Comment;
import example.community.domain.Member;
import example.community.domain.Post;
import example.community.repository.CommentRepository;
import example.community.repository.MemberRepository;
import example.community.repository.PostRepository;
import example.community.service.dto.CommentDto;
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
        Post post = postRepository.findById(post_id).orElseThrow(IllegalAccessError::new);
        Member member = memberRepository.findById(member_id).orElseThrow(IllegalAccessError::new);

        Comment comment = Comment.createComment(commentDto.getBody(), member, post);
        commentRepository.save(comment);
        post.plusCommentNum();
    }

    @Transactional
    public void update(Long comment_id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(IllegalAccessError::new);
        comment.update(commentDto.getBody());
    }

    @Transactional
    public void delete(Long comment_id, Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(IllegalAccessError::new);
        commentRepository.deleteById(comment_id);
        post.minusCommentNum();
    }
}
