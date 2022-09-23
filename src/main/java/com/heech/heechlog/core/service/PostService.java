package com.heech.heechlog.core.service;

import com.heech.heechlog.core.repository.PostQueryRepository;
import com.heech.heechlog.core.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostQueryRepository postQueryRepository;
    private final PostRepository postRepository;

    /**
     * Post 목록 조회
     */

    /**
     * Post 단건 조회
     */

    /**
     * Post 저장
     */

    /**
     * Post 수정
     */

    /**
     * Post 삭제
     */

}
