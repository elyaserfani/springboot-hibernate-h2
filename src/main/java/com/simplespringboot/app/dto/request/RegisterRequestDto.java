package com.simplespringboot.app.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class RegisterRequestDto implements Serializable {
    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "Enter your username", type = "string", example = "username")
    private String username;

    @NotEmpty
    @Schema(description = "Enter your roles", type = "array")
    private Set<@NotBlank String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "Enter your password", type = "string", example = "password")
    private String password;

}