package com.miportfolio.comment_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostRequest {
    @NotBlank(message = "El título es obligatorio")
    private String title;
    @NotBlank(message = "El resumen es obligatorio")
    private String summary;
    @NotBlank(message = "El contenido no puede estar vacío")
    private String content;
    private String excerpt;
    @NotBlank(message = "El autor del post no puede estar vacío")
    private String author;
    private List<String> tags = new ArrayList<>();
}
