package example.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /********************* 게시글 CRUD ************************************/

    /**
     * 게시글리스트 read
     */
    @GetMapping("/post")
    public String post(Model model) {
        List<PostListDto> postList = postService.findList();
        model.addAttribute("postListDto", postList);

        return "post/list";
    }

    /**
     * 게시글 read
     */
    @GetMapping("/post/{post_id}")
    public String post(@PathVariable Long post_id, Model model) {
        PostDto postDto = postService.findPostAndComment(post_id);
        model.addAttribute("postDto", postDto);

        return "post/post";
    }

    /**
     * 게시글 등록 폼
     */
    @GetMapping("/post/add")
    public String addPostForm(@ModelAttribute PostDto postDto) {
        return "post/addform";
    }

    /**
     * 게시글 수정 폼
     */
    @GetMapping("/post/{post_id}/edit")
    public String editPostForm(@PathVariable Long post_id, Model model) {
        PostDto postDto = postService.findPostDto(post_id);
        model.addAttribute("postDto", postDto);

        return "post/editform";
    }
}
