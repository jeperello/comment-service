package com.miportfolio.comment_service.controller;

import com.miportfolio.comment_service.dto.CommentRequest;
import com.miportfolio.comment_service.model.Comment;
import com.miportfolio.comment_service.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "API para gestionar los comentarios del portfolio")
public class CommentController {

    private final CommentRepository repository;

    @Operation(summary = "Obtener todos los comentarios", description = "Retorna una lista de todos los comentarios realizados")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getAll() {
        return repository.findAll();
    }

    @Operation(summary = "Crear un nuevo comentario", description = "Guarda un comentario. Si el nombre está vacío, se guarda como Anónimo")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@Valid @RequestBody CommentRequest request) {
        Comment comment = new Comment();
        comment.setUsername(request.getUsername() == null || request.getUsername().isBlank() ? "Anónimo" : request.getUsername());
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        return repository.save(comment);
    }
}
