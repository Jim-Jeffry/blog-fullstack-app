package com.blog.repository;

import com.blog.entity.Blog;
import com.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Fetch all published blogs (with pagination)
    Page<Blog> findByPublishedTrue(Pageable pageable);

    // Fetch blogs by a specific author (with pagination)
    Page<Blog> findByAuthor(User author, Pageable pageable);
}
