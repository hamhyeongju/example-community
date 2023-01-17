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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @brief member_id(pk, fk) 를 비교 하여 Post 접근 권한 인가
 */
@RequiredArgsConstructor
public class PostInterceptor implements HandlerInterceptor {

    private final PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Member loginMember = userDetails.getMember();

        HandlerMethod method = (HandlerMethod) handler;

        int pos = requestURI.lastIndexOf("/");
        Long postId = Long.parseLong(requestURI.substring(pos + 1));

        // 단순 조회 로직일 경우
        if (method.getMethod().getName().equals("post")) return true;

        Post post = postService.findPostForInterceptor(postId);
        if (!post.getMember().getId().equals(loginMember.getId())) {
            response.sendRedirect("/post");
            return false;
        }
        return true;
    }
}
