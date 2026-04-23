package com.miportfolio.comment_service.service;

import static org.junit.jupiter.api.Assertions.*;

import com.miportfolio.comment_service.dto.CommentRequest;
import com.miportfolio.comment_service.model.Comment;
import com.miportfolio.comment_service.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository repository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void whenUsernameIsEmpty_thenSaveAsAnonimo() {
        // Arrange (Preparar datos)
        CommentRequest request = new CommentRequest();
        request.setContent("Test comment");
        request.setUsername(""); // Vacío

        Comment savedComment = new Comment();
        savedComment.setUsername("Anónimo");
        savedComment.setContent("Test comment");

        when(repository.save(any(Comment.class))).thenReturn(savedComment);

        // Act (Ejecutar)
        Comment result = commentService.saveComment(request);

        // Assert (Verificar)
        assertEquals("Anónimo", result.getUsername());
        verify(repository, times(1)).save(any(Comment.class));
    }
}