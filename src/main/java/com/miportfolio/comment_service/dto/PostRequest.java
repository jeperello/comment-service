package com.miportfolio.comment_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {
    @NotBlank(message = "El título es obligatorio")
    private String title;
    @NotBlank(message = "El resumen es obligatorio")
    private String summary;
    @NotBlank(message = "El contenido no puede estar vacío")
    private String content;
}
