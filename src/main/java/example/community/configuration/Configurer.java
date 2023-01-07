package example.community.configuration;

import example.community.configuration.interceptor.CommentInterceptor;
import example.community.configuration.interceptor.PostInterceptor;
import example.community.domain.Post;
import example.community.service.CommentService;
import example.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class Configurer implements WebMvcConfigurer {

    private final PostService postService;
    private final CommentService commentService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/join", "/login", "/member/**", "/error", "/css/**", "/js/**").permitAll()
                .antMatchers("/post/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/post")
                .and()
                .logout()
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PostInterceptor(postService))
                .order(1)
                .excludePathPatterns("/post/add")
                .addPathPatterns("/post/edit/*", "/post/*");

        registry.addInterceptor(new CommentInterceptor(commentService))
                .order(2)
                .addPathPatterns("/post/*/comment/edit/*", "/post/*/comment/*");
    }
}
