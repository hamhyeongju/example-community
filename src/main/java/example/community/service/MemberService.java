package example.community.service;

import example.community.domain.Comment;
import example.community.domain.Heart;
import example.community.domain.Member;
import example.community.repository.MemberRepository;
import example.community.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void save(MemberJoinDto memberJoinDto) {

       if (memberRepository.existsByLoginId(memberJoinDto.getLoginId())) throw new IllegalArgumentException();

       memberRepository.save(Member.createMember(memberJoinDto.getLoginId(),
                bCryptPasswordEncoder.encode(memberJoinDto.getPassword()),
                memberJoinDto.getName().strip()));
    }

    @Transactional
    public void delete(Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(IllegalAccessError::new);

        List<Comment> comments = member.getComments();
        comments.forEach(comment -> comment.getPost().minusCommentNum());

        List<Heart> hearts = member.getHearts();
        hearts.forEach(like -> like.getPost().minusHeartNum());

        memberRepository.delete(member);
    }
}
