package com.heech.heechlog.core.post.repository;

import com.heech.heechlog.core.PostTestConfig;
import com.heech.heechlog.core.post.domain.Post;
import com.heech.heechlog.core.post.dto.PostSearchCondition;
import com.heech.heechlog.core.post.dto.SearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostTestConfig.class)
class PostQueryRepositoryTest {

    //CREATE_POST
    public static final String POST_TITLE = "post_title";
    public static final String POST_CONTENT = "post_content";

    @PersistenceContext EntityManager em;

    @Autowired PostQueryRepository postQueryRepository;

    private Post getPost(String title, String content) {
        return Post.createPost()
                .title(title)
                .content(content)
                .build();
    }

    @Test
    @DisplayName("post 목록 조회")
    void findPosts() {
        //given
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            em.persist(getPost(POST_TITLE + i, POST_CONTENT));
        }

        PostSearchCondition condition = new PostSearchCondition();
        condition.setSearchCondition(SearchCondition.TITLE);
        condition.setSearchKeyword("0");
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Post> contents = postQueryRepository.findPosts(condition, pageRequest);

        //then
        assertThat(contents.getTotalElements()).isEqualTo(3);
        assertThat(contents.getContent().size()).isEqualTo(3);

    }
}