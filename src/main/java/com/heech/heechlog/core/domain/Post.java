package com.heech.heechlog.core.domain;

import com.heech.heechlog.common.entity.BaseEntity;
import com.heech.heechlog.core.dto.UpdatePostParam;
import lombok.AccessLevel;
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

    private String title;

    @Lob
    private String content;

    private Integer hits;

    public void updatePost(UpdatePostParam param) {

    }

}
