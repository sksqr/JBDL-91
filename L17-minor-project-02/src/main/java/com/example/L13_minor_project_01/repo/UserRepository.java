package com.example.L13_minor_project_01.repo;

import com.example.L13_minor_project_01.entity.User;
import com.example.L13_minor_project_01.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    java.util.List<User> findAllByRole(UserRole role);

    Page<User> findAllByRole(UserRole role, Pageable pageable);

    User findByEmail(String email);
}
