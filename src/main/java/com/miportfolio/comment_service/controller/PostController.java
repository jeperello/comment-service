package com.miportfolio.comment_service.controller;

import com.miportfolio.comment_service.PostService;
import com.miportfolio.comment_service.dto.CommentRequest;
import com.miportfolio.comment_service.dto.PostRequest;
import com.miportfolio.comment_service.model.Comment;
import com.miportfolio.comment_service.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Blog Posts", description = "Endpoints para gestionar las publicaciones del blog y sus comentarios")
public class PostController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "Listar todos los posts")
    public List<Post> getAll() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un post por ID")
    public Post getById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva publicación")
    public Post create(@Valid @RequestBody PostRequest request) {
        return postService.createPost(request);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar un comentario a un post específico")
    public Comment addComment(@PathVariable Long id, @Valid @RequestBody CommentRequest request) {
        return postService.addCommentToPost(id, request);
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Obtener todos los comentarios de un post específico")
    public List<Comment> getComments(@PathVariable Long id) {
        return postService.getCommentsByPostId(id);
    }
}
