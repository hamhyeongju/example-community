package example.community.service;

import example.community.domain.Member;
import example.community.repository.CommentRepository;
import example.community.repository.HeartRepository;
import example.community.repository.MemberRepository;
import example.community.repository.PostRepository;
import example.community.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
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
        heartRepository.deleteByMemberId(member_id);
        commentRepository.deleteByMemberId(member_id);
        postRepository.deleteByMemberId(member_id);
        memberRepository.deleteByMemberId(member_id);
    }
}
