package com.simplespringboot.app.controller;

import com.fanapium.keylead.client.exception.ClientOperationException;
import com.fanapium.keylead.client.exception.UserOperationException;
import com.fanapium.keylead.common.oauth.exception.OAuthException;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.service.PodService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pod")

public class PodController {

    @Autowired
    private PodService podService;

    @GetMapping("/init")
    @ApiOperation(value = "Login initialize for pod accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login link generated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
    })
    public ResponseEntity<?> loginInitialize() throws OAuthException, ClientOperationException, UnsupportedEncodingException, NoSuchAlgorithmException {
       return podService.loginInitialize();
    }
    @GetMapping("/getCode")
    @ApiIgnore
    public String getCode(@RequestParam("code") String code) {
        return "<h1>"+code+"</h1>";
    }
    @GetMapping("/login")
    @ApiOperation(value = "Login with code and generate access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
    })
    public String login(@RequestParam("code") String code) throws OAuthException, ClientOperationException, UserOperationException, EncoderException, NoSuchAlgorithmException, URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        return podService.login(code);
    }

}
