package com.heech.heechlog.core.like.domain;

import com.heech.heechlog.common.entity.BaseTimeEntity;
import com.heech.heechlog.core.post.domain.Post;
import com.heech.heechlog.core.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Like 생성
     */
    @Builder(builderMethodName = "createLike")
    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
