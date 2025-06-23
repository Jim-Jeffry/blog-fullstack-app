package com.blog.service;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepo;

    @Override
    public Blog createPost(Blog blog, User user) {
        blog.setAuthor(user);
        return blogRepo.save(blog);
    }

    @Override
    public Page<Blog> getPublishedPosts(int page, int size) {
        return blogRepo.findByPublishedTrue(PageRequest.of(page, size));
    }

    @Override
    public Page<Blog> getUserPosts(User user, int page, int size) {
        return blogRepo.findByAuthor(user, PageRequest.of(page, size));
    }

    @Override
    public Blog updatePost(Long id, Blog updatedBlog, User user) {
        Blog blog = blogRepo.findById(id).orElseThrow();
        if (!blog.getAuthor().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized");
        }
        blog.setTitle(updatedBlog.getTitle());
        blog.setContent(updatedBlog.getContent());
        blog.setPublished(updatedBlog.isPublished());
        return blogRepo.save(blog);
    }

    @Override
    public void deletePost(Long id, User user) {
        Blog blog = blogRepo.findById(id).orElseThrow();
        if (!blog.getAuthor().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized");
        }
        blogRepo.delete(blog);
    }
}
