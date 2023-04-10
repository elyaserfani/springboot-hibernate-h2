package com.simplespringboot.app.dto.request;


import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {
    @NotBlank
    @Schema(description = "Enter your username", type = "string", example = "username")
    private String username;

    @NotBlank
    @Schema( description = "Enter your password", type = "string", example = "password")
    private String password;
}