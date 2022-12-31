package example.community.configuration.interceptor;

import example.community.configuration.security.UserDetailsImpl;
import example.community.domain.Member;
import example.community.domain.Post;
import example.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 자신의 post 에만 수정(get 요청 포함), 삭제 요청 가능
 */
@RequiredArgsConstructor
public class PostInterceptor implements HandlerInterceptor {

    private final PostService postService;

    private final String POST = "public java.lang.String example.community.controller.PostController.post(java.lang.Long,org.springframework.ui.Model,example.community.service.dto.CommentDto,example.community.configuration.security.UserDetailsImpl)";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
//        Member loginMember = (Member) request.getSession(false).getAttribute("loginMember");

        // SecurityContextHolder로 부터 사용자 정보를 얻음
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Member loginMember = userDetails.getMember();

        HandlerMethod method = (HandlerMethod) handler;


        // 요청 uri에서 id 값을 파싱
        int pos = requestURI.lastIndexOf("/");
        Long postId = Long.parseLong(requestURI.substring(pos + 1));

        if (method.getMethod().toString().equals(POST)) {
            return true;
        }

        Post post = postService.findPostForInterceptor(postId);
        if (!post.getMember().getId().equals(loginMember.getId())) {
            response.sendRedirect("/post");
            return false;
        }
        return true;
    }
}
