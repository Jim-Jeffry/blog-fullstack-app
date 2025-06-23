package com.blog.repository;

import com.blog.entity.Post;
import com.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);  // 👈 This will be used in /user?email=...
}
