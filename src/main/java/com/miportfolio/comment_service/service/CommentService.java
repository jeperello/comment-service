package com.miportfolio.comment_service.service;

import com.miportfolio.comment_service.dto.CommentRequest;
import com.miportfolio.comment_service.model.Comment;
import com.miportfolio.comment_service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    @Transactional
    public Comment saveComment(CommentRequest request) {
        Comment comment = new Comment();

        // Lógica de negocio centralizada
        String finalUsername = (request.getUsername() == null || request.getUsername().isBlank())
                ? "Anónimo"
                : request.getUsername();

        comment.setUsername(finalUsername);
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        return repository.save(comment);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: comentario no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
}
