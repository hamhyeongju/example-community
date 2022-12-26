package example.community.controller;

import example.community.service.MemberService;
import example.community.service.dto.LoginDto;
import example.community.service.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 폼
     */
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberDto") MemberJoinDto memberJoinDto) {
        return "member/join";
    }

    /**
     * 로그인 폼
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto,
                            @AuthenticationPrincipal UserDetails userDetails) {
        return (userDetails == null) ? "member/login" : "redirect:/";
    }

    /**
     * 회원가입
     */
    @PostMapping("/member")
    public String join(@ModelAttribute MemberJoinDto memberJoinDto) {
        memberService.save(memberJoinDto);
        return "redirect:/";
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/member/{member_id}")
    public String deleteMember(@PathVariable Long member_id) {
        memberService.delete(member_id);
        return "redirect:/";
    }

}
