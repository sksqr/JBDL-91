package com.example.L13_minor_project_01.repo;

import com.example.L13_minor_project_01.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);
}
