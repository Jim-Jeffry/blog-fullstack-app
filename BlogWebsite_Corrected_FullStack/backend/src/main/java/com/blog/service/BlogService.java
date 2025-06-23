package com.blog.service;

import com.blog.entity.Blog;
import com.blog.entity.User;
import org.springframework.data.domain.Page;

public interface BlogService {
    Blog createPost(Blog blog, User user);
    Page<Blog> getPublishedPosts(int page, int size);
    Page<Blog> getUserPosts(User user, int page, int size);
    Blog updatePost(Long id, Blog updatedBlog, User user);
    void deletePost(Long id, User user);
}
