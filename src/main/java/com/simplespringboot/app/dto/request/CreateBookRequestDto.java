package com.simplespringboot.app.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplespringboot.app.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class CreateBookRequestDto implements Serializable {
    @NotBlank
    @Schema(description = "Enter your book name", type = "string", example = "Book Name")
    private String name;

    @NotBlank
    @Schema(description = "Enter your book description", type = "string", example = "Book Description")
    private String description;

    @NotNull
    @Schema(description = "Enter shaparak code", type = "integer", example = "15151515")
    private Integer shaparak_code;

    @JsonIgnore
    private User author;
}