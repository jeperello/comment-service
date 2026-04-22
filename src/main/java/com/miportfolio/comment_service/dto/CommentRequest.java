package com.miportfolio.comment_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
    @Size(max = 50, message = "El nombre es muy largo")
    private String username; // Si viene vacío, lo trataremos como "Anónimo" en el Service

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(min = 2, max = 500, message = "El comentario debe tener entre 2 y 500 caracteres")
    private String content;
}
