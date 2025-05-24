package com.NamVu.ReviewSpring.repository;

import com.NamVu.ReviewSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    void deleteByIdIn(Set<Long> userIds);
}
