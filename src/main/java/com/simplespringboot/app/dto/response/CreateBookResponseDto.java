package com.simplespringboot.app.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookResponseDto implements Serializable {
    @Schema(description = "Book id", type = "integer", example = "1")
    private Long id;
    @Schema(description = "Book name", type = "string", example = "Book Name")
    private String name;
    @Schema(description = "Book description", type = "string", example = "Book description")
    private String description;
}