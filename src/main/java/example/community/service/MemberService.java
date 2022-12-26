package example.community.service;

import example.community.domain.Comment;
import example.community.domain.Heart;
import example.community.domain.Member;
import example.community.repository.MemberRepository;
import example.community.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberJoinDto memberJoinDto) {
        memberRepository.save(Member.createMember(memberJoinDto.getLoginId(), memberJoinDto.getPassword(), memberJoinDto.getName().strip()));
    }

    public void delete(Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(IllegalAccessError::new);

        List<Comment> comments = member.getComments();
        comments.forEach(comment -> comment.getPost().minusCommentNum());

        List<Heart> hearts = member.getHearts();
        hearts.forEach(like -> like.getPost().minusHeartNum());

        memberRepository.delete(member);
    }
}
