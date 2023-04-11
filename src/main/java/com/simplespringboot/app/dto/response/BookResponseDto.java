package com.simplespringboot.app.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    @Schema(description = "Book id", type = "integer", example = "1")
    private Long id;
    @Schema(description = "Book name", type = "string", example = "Book Name")
    private String name;
    @Schema(description = "Book description", type = "string", example = "Book description")
    private String description;

}