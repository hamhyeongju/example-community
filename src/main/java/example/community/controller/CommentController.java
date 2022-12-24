package example.community.controller;

import example.community.configuration.security.UserDetailsImpl;
import example.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping("/post/{post_id}/comment")
    public String addComment(@PathVariable Long post_id, RedirectAttributes redirectAttributes,
                             @ModelAttribute CommentDto commentDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.save(commentDto, post_id, userDetails.getMember().getId());
        redirectAttributes.addAttribute("post_id", post_id);

        return "redirect:/post/{post_id}";
    }


}
