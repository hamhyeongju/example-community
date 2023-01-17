package example.community.configuration.interceptor;

import example.community.configuration.security.UserDetailsImpl;
import example.community.domain.Comment;
import example.community.domain.Member;
import example.community.domain.Post;
import example.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @brief member_id(pk, fk) 를 비교 하여 Comment 접근 권한 인가
 */
@RequiredArgsConstructor
public class CommentInterceptor implements HandlerInterceptor {

    private final CommentService commentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Member loginMember = userDetails.getMember();

        int pos = requestURI.lastIndexOf("/");
        Long comment_id = Long.parseLong(requestURI.substring(pos + 1));

        Comment comment = commentService.findCommentForInterceptor(comment_id);
        if (!comment.getMember().getId().equals(loginMember.getId())) {
            response.sendRedirect("/post");
            return false;
        }
        return true;
    }
}
