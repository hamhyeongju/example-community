package example.community.controller;

import example.community.configuration.security.UserDetailsImpl;
import example.community.service.CommentService;
import example.community.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 댓글 수정
     */
    @PutMapping("/post/{post_id}/comment/{comment_id}")
    public String updateComment(@PathVariable Long post_id, @PathVariable Long comment_id,
                                @ModelAttribute CommentDto commentDto, RedirectAttributes redirectAttributes) {

        commentService.update(comment_id, commentDto);
        redirectAttributes.addAttribute("post_id", post_id);

        return "redirect:/post/{post_id}";
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/post/{post_id}/comment/{comment_id}")
    public String deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id,
                                RedirectAttributes redirectAttributes) {
        commentService.delete(comment_id, post_id);
        redirectAttributes.addAttribute("post_id", post_id);

        return "redirect:/post/{post_id}";
    }


}
