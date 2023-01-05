package com.heech.heechlog.core.user.repository;

import com.heech.heechlog.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
}
