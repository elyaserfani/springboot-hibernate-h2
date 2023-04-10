package com.simplespringboot.app.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

    @Schema(description = "User id", type = "integer", example = "1")
    private Long id;
    @Schema(description = "User username", type = "string", example = "username")
    private String username;
    @Schema(description = "User roles", type = "array")
    private List<String> roles;
    @Schema(description = "Token type", type = "string", example = "Bearer")
    private String type = "Bearer";
    @Schema(description = "User access token", type = "string", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY4MTExMDI0OCwiZXhwIjoxNjgxMTk2NjQ4fQ.MpsVkoYnAR7pNU6-Ecf5ORcW3aJyqf8LqlIGvTVnxU_QI4AzGFkgjiTF93cImIG7voKSwiXdTiGnoWoefGKzCQ")
    private String token;

    public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}