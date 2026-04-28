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
        post.setExcerpt(request.getExcerpt());
        post.setAuthor(request.getAuthor());
        post.setTags(request.getTags());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setSummary(request.getSummary());
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

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post no encontrado con ID: " + postId);
        }
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public Post updatePost(Long id, PostRequest request) {
        // Buscamos el post existente
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Post no encontrado con ID: " + id));
        // Actualizamos solo los campos necesarios
        post.setExcerpt(request.getExcerpt());
        post.setAuthor(request.getAuthor());
        post.setTags(request.getTags());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setSummary(request.getSummary());
        // No tocamos post.setCreatedAt para mantener la fecha original
        return postRepository.save(post);
    }

    @Transactional
    public Post patchPost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede parchar: Post no encontrado con ID: " + id));

        // Si el título viene en el JSON, lo actualizamos
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            post.setTitle(request.getTitle());
        }

        // Si el contenido viene en el JSON, lo actualizamos
        if (request.getContent() != null && !request.getContent().isBlank()) {
            post.setContent(request.getContent());
        }

        // Si el contenido viene en el JSON, lo actualizamos
        if (request.getExcerpt() != null && !request.getExcerpt().isBlank()) {
            post.setExcerpt(request.getExcerpt());
        }

        // Si el resumen viene en el JSON, lo actualizamos
        if (request.getSummary() != null && !request.getSummary().isBlank()) {
            post.setSummary(request.getSummary());
        }

        // Si tags viene en el JSON, lo actualizamos
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            post.setTags(request.getTags());
        }

        return postRepository.save(post);
    }
}
