package example.community.service;

import example.community.domain.Heart;
import example.community.domain.Member;
import example.community.domain.Post;
import example.community.repository.HeartRepository;
import example.community.repository.MemberRepository;
import example.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;

    public void changeHeartStatus(Long post_id, Long member_id) {

        heartRepository.findByMemberIdAndPostId(post_id, member_id).ifPresentOrElse(
                heart -> {
                    heartRepository.delete(heart);
                    heart.getPost().minusHeartNum();
                },
                () -> {
                    Post post = postRepository.findById(post_id).orElseThrow(IllegalAccessError::new);
                    Member member = memberRepository.findById(member_id).orElseThrow(IllegalAccessError::new);

                    heartRepository.save(Heart.createHeart(post, member));
                    post.plusHeartNum();
                });
    }
}
