package com.simplespringboot.app.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplespringboot.app.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CreateBookRequest implements Serializable {
    @NotBlank
    @Schema(description = "Enter your book name", type = "string", example = "Book Name")
    private String name;

    @NotBlank
    @Schema(description = "Enter your book description", type = "string", example = "Book Description")
    private String description;

    @JsonIgnore
    private User author;
}