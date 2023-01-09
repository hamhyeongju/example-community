package example.community.repository.customRepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.community.domain.Post;
import example.community.domain.QMember;
import example.community.domain.QPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static example.community.domain.QMember.member;
import static example.community.domain.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory querydsl;

    public PostRepositoryImpl(EntityManager em) {
        this.querydsl = new JPAQueryFactory(em);
    }


    @Override
    public Page<Post> findAllPageAndSearch(Pageable pageable, PostSearch postSearch) {
        return null;
    }
}
