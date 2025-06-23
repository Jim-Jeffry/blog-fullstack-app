package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.repository.UserRepository;
import com.blog.security.JwtUtil;
import com.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    // Create new blog post
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Blog blog, Authentication auth) {
        User user = getUserFromAuth(auth);
        Blog created = blogService.createPost(blog, user);
        return ResponseEntity.ok(created);
    }

    // Get all published posts
    @GetMapping("/all")
    public ResponseEntity<?> getAllPublished(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getPublishedPosts(page, size));
    }

    // Get posts created by current user
    @GetMapping("/my-posts")
    public ResponseEntity<?> getMyPosts(Authentication auth,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        User user = getUserFromAuth(auth);
        return ResponseEntity.ok(blogService.getUserPosts(user, page, size));
    }

    // Update a blog post
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody Blog blog,
                                        Authentication auth) {
        User user = getUserFromAuth(auth);
        return ResponseEntity.ok(blogService.updatePost(id, blog, user));
    }

    // Delete a blog post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication auth) {
        User user = getUserFromAuth(auth);
        blogService.deletePost(id, user);
        return ResponseEntity.ok(Map.of("message", "Post deleted"));
    }

    private User getUserFromAuth(Authentication auth) {
        String email = auth.getName();
        return userRepo.findByEmail(email).orElseThrow();
    }
}
