package example.community.repository.customRepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import example.community.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static example.community.domain.QMember.member;
import static example.community.domain.QPost.post;

/**
 * @brief JPARepository 확장, Querydsl 사용한 동적 쿼리 기반 조회 로직
 */
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory querydsl;

    public PostRepositoryImpl(EntityManager em) {
        this.querydsl = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findAllPageAndSearch(Pageable pageable, PostSearch postSearch) {
        List<Post> result = querydsl.selectFrom(post).join(post.member, member).fetchJoin()
                .where(searchTypeAndWord(postSearch))
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Long> count = querydsl.select(post.count()).from(post).join(post.member, member)
                .where(searchTypeAndWord(postSearch));         ;

        return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);
    }

    private BooleanExpression searchTypeAndWord(PostSearch postSearch) {

        if (postSearch.isBlank()) return null;

        switch (postSearch.getSearchType()) {
            case member: return post.member.name.eq(postSearch.getSearchWord());

            case title: return post.title.contains(postSearch.getSearchWord());

            case body: return post.body.contains(postSearch.getSearchWord());

            default: return null;
        }
    }
}
