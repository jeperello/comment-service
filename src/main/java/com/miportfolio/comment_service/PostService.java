package com.miportfolio.comment_service;

import com.miportfolio.comment_service.dto.CommentRequest;
import com.miportfolio.comment_service.dto.PostRequest;
import com.miportfolio.comment_service.model.Comment;
import com.miportfolio.comment_service.model.Post;
import com.miportfolio.comment_service.repository.CommentRepository;
import com.miportfolio.comment_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado con ID: " + id));
    }

    @Transactional
    public Post createPost(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Transactional
    public Comment addCommentToPost(Long postId, CommentRequest request) {
        Post post = getPostById(postId);

        Comment comment = new Comment();
        comment.setUsername(request.getUsername() == null || request.getUsername().isBlank() ? "Anónimo" : request.getUsername());
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(post);

        return commentRepository.save(comment);
    }
}
