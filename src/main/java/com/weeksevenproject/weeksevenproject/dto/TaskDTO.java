package com.weeksevenproject.weeksevenproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    
    @NotBlank(message = "Field is required")
    private String title;

    @NotBlank(message = "Field is required")
    private String summary;

    @NotBlank(message = "Field is required")
    private String content;

}
