package com.simplespringboot.app.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest implements Serializable {
    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "Enter your username", type = "string", example = "username")
    private String username;

    @Schema(description = "Enter your roles", type = "array")
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "Enter your password", type = "string", example = "password")
    private String password;

}