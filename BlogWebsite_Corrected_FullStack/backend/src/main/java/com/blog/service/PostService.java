package com.blog.service;

import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;

    public void deletePostById(Long id, String email) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to delete this post");
        }

        postRepo.delete(post);
    }
}
