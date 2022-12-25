package example.community.controller;

import example.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 폼
     */
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberDto") MemberDto memberDto) {
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

}
