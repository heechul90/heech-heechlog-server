package com.heech.heechlog.core.post.dto;

import com.heech.heechlog.common.dto.CommonSearchCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchCondition extends CommonSearchCondition {

    private SearchCondition searchCondition;

}
