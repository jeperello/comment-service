package com.miportfolio.comment_service.controller;

import com.miportfolio.comment_service.service.PostService;
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

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un post existente", description = "Modifica el título y contenido de un post por su ID")
    @ResponseStatus(HttpStatus.OK)
    public Post update(@PathVariable Long id, @Valid @RequestBody PostRequest request) {
        return postService.updatePost(id, request);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Actualización parcial de un post",
            description = "Permite modificar solo algunos campos sin necesidad de enviar el objeto completo"
    )

    @ResponseStatus(HttpStatus.OK)
    public Post patch(@PathVariable Long id, @RequestBody PostRequest request) {
        return postService.patchPost(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un post", description = "Elimina el post y todos sus comentarios asociados")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content es el estándar para deletes exitosos
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
