package com.heech.heechlog.core.post.repository;

import com.heech.heechlog.core.post.domain.Post;
import com.heech.heechlog.core.post.dto.PostSearchCondition;
import com.heech.heechlog.core.post.dto.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.heechlog.core.post.domain.QPost.post;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * Post 목록 조회
     */
    public Page<Post> findPosts(PostSearchCondition condition, Pageable pageable) {

        List<Post> content = getPostList(condition, pageable);

        JPAQuery<Long> count = getPostListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * Post 목록
     */
    private List<Post> getPostList(PostSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(post)
                .from(post)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();
    }

    /**
     * Post 목록 카운트
     */
    private JPAQuery<Long> getPostListCount(PostSearchCondition condition) {
        return queryFactory
                .select(post.count())
                .from(post)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                );
    }

    /**
     * where searchCondition LIKE '%searchKeyword%'
     */
    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null || !hasText(searchKeyword)) {
            return null;
        }

        if (SearchCondition.TITLE.equals(searchCondition)) {
            return post.title.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return post.content.contains(searchKeyword);
        }

        return null;
    }
}
