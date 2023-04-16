package com.simplespringboot.app.controller;

import com.fanapium.keylead.client.exception.ClientOperationException;
import com.fanapium.keylead.client.exception.UserOperationException;
import com.fanapium.keylead.common.oauth.exception.OAuthException;
import com.simplespringboot.app.dto.request.CreateBookRequestDto;
import com.simplespringboot.app.dto.response.BookResponseDto;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.global.CustomPageDto;
import com.simplespringboot.app.service.book.BookService;
import com.simplespringboot.app.service.pod.PodService;
import com.simplespringboot.app.service.user.UserService;
import com.simplespringboot.app.utility.JwtUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pod")
@Tag(name = "pod",description = "Pod Controller")

public class PodController {

    @Autowired
    private PodService podService;

    @GetMapping("/init")
    @Operation(summary = "Login initialize for pod accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login link generated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
    })
    public ResponseEntity<?> loginInitialize() throws OAuthException, ClientOperationException, UnsupportedEncodingException, NoSuchAlgorithmException {
       return podService.loginInitialize();
    }
    @GetMapping("/getCode")
    @Hidden
    public String getCode(@RequestParam("code") String code) {
        return "<h1>"+code+"</h1>";
    }
    @GetMapping("/login")
    @Operation(summary = "Login with code and generate access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
    })
    public String login(@RequestParam("code") String code) throws OAuthException, ClientOperationException, UserOperationException, EncoderException, NoSuchAlgorithmException {
        return podService.login(code);
    }

}
