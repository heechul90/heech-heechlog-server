package com.heech.heechlog.core.domain;

import com.heech.heechlog.common.entity.BaseEntity;
import com.heech.heechlog.core.dto.UpdatePostParam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String title;

    @Lob
    @Column(name = "post_content")
    private String content;

    private Integer hits;

    //===생성 메서드===//
    /** 저장 */
    @Builder(builderClassName = "createPostBuilder", builderMethodName = "createPostBuilder")
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.hits = 0;
    }

    /** 수정 */
    public void updatePost(UpdatePostParam param) {
        this.title = param.getTitle();
        this.content = param.getContent();
    }

    //===업데이트 메서드===//
    public void hit() {
        this.hits += 1;
    }
}
