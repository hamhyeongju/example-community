package example.community.controller;

import example.community.configuration.security.UserDetailsImpl;
import example.community.repository.customRepository.PostSearch;
import example.community.service.HeartService;
import example.community.service.PostService;
import example.community.service.dto.CommentDto;
import example.community.service.dto.PostDto;
import example.community.service.dto.PostListDto;
import example.community.service.dto.WritePostDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final HeartService heartService;

    private static int getStartPage(Page<PostListDto> postList) {
        return (postList.getNumber() < 5) ? 1 : ((postList.getNumber() / 5) * 5) + 1;
    }

    private static int getEndPage(Page<PostListDto> postList, int startPage) {
        return Math.min(postList.getTotalPages(), startPage + 4);
    }

    /**
     * 게시글리스트 read
     */
    @GetMapping("/post")
    public String postList(Model model, @ModelAttribute("postSearch") PostSearch postSearch,
                           @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostListDto> postList = postService.findList(pageable, postSearch);
        model.addAttribute("postListDto", postList.getContent());

        int startPage = getStartPage(postList);

        model.addAttribute("startPage", getStartPage(postList));
        model.addAttribute("currentPage", postList.getNumber() + 1);
        model.addAttribute("endPage", getEndPage(postList, startPage));
        model.addAttribute("hasPrevious", postList.hasPrevious());
        model.addAttribute("hasNext", postList.hasNext());

        return "post/list";
    }

    /**
     * 게시글 read
     */
    @GetMapping("/post/{post_id}")
    public String post(@PathVariable Long post_id, Model model,
                       @ModelAttribute("commentDto") CommentDto commentDto,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostDto postDto = postService.findPostAndComment(post_id);
        model.addAttribute("postDto", postDto);
        model.addAttribute("guest_id", userDetails.getMember().getId());

        return "post/post";
    }

    /**
     * 게시글 등록 폼
     */
    @GetMapping("/post/add")
    public String addPostForm(@ModelAttribute("postDto") WritePostDto writePostDto) {
        return "post/addform";
    }

    /**
     * 게시글 수정 폼
     */
    @GetMapping("/post/edit/{post_id}")
    public String editPostForm(@PathVariable Long post_id, Model model) {
        WritePostDto writePostDto = postService.findWritePostDto(post_id);
        model.addAttribute("postDto", writePostDto);

        return "post/editform";
    }

    /**
     * 게시글 등록
     */
    @PostMapping("/post")
    public String createPost(@ModelAttribute WritePostDto writePostDto, RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long post_id = postService.createPost(writePostDto, userDetails.getMember().getId());
        redirectAttributes.addAttribute("post_id", post_id);

        return "redirect:/post/{post_id}";
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/post/{post_id}")
    public String updatePost(@PathVariable Long post_id,
                             @ModelAttribute WritePostDto writePostDto,
                             RedirectAttributes redirectAttributes) {

        if (post_id != null) postService.updatePost(post_id, writePostDto);

        redirectAttributes.addAttribute("post_id", post_id);

        return "redirect:/post/{post_id}";
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{post_id}")
    public String deletePost(@PathVariable Long post_id) {
        if (post_id != null) postService.delete(post_id);

        return "redirect:/post";
    }

    /**
     * 좋아요 기능
     */
    @PostMapping("/post/{post_id}/heart")
    public String changeHeartStatus(@PathVariable Long post_id,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                    RedirectAttributes redirectAttributes) {

        heartService.changeHeartStatus(post_id, userDetails.getMember().getId());

        redirectAttributes.addAttribute("post_id", post_id);
        return "redirect:/post/{post_id}";
    }

    @Getter
    public static class PageDto {
        int pageSize = 5; // 출력할 페이지 개수

    }
}
