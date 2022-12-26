package example.community.service;

import example.community.domain.Member;
import example.community.repository.MemberRepository;
import example.community.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberJoinDto memberJoinDto) {
        memberRepository.save(Member.createMember(memberJoinDto.getLoginId(), memberJoinDto.getPassword(), memberJoinDto.getName().strip()));
    }
}
