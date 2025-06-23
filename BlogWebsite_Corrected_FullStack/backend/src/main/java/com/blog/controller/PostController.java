package com.blog.controller;

import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post, Authentication authentication) {
        String email = authentication.getName(); // Extracted from JWT
        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        postRepo.save(post);

        return ResponseEntity.ok("Post published successfully");
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postRepo.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserPosts(@RequestParam String email) {
        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(postRepo.findByUser(user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id,
                                             @RequestBody Post updatedPost,
                                             Authentication authentication) {
        String email = authentication.getName();
        Post existingPost = postRepo.findById(id).orElse(null);

        if (existingPost == null) {
            return ResponseEntity.status(404).body("Post not found");
        }

        // Optional: Only allow post owner to update
        if (!existingPost.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body("You are not allowed to update this post");
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        postRepo.save(existingPost);

        return ResponseEntity.ok("Post updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id, Authentication authentication) {
        Post post = postRepo.findById(id).orElse(null);

        if (post == null) {
            return ResponseEntity.status(404).body("Post not found");
        }

        // Optional: Allow only owner to fetch their post
        String email = authentication.getName();
        if (!post.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body("Unauthorized to access this post");
        }

        return ResponseEntity.ok(post);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Principal principal) {
        postService.deletePostById(id, principal.getName());
        return ResponseEntity.ok("Post deleted successfully");
    }



}
