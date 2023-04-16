package com.simplespringboot.app.service.pod;

import com.fanapium.keylead.client.KeyleadClient;
import com.fanapium.keylead.client.KeyleadClientFactory;
import com.fanapium.keylead.client.exception.ClientOperationException;
import com.fanapium.keylead.client.exception.UserOperationException;
import com.fanapium.keylead.client.users.ModifiableUser;
import com.fanapium.keylead.client.users.User;
import com.fanapium.keylead.client.users.Users;
import com.fanapium.keylead.client.vo.ClientCredentials;
import com.fanapium.keylead.client.vo.NewUserVo;
import com.fanapium.keylead.common.KeyleadUserVo;
import com.fanapium.keylead.common.oauth.exception.OAuthException;
import com.fanapium.keylead.common.oauth.vo.OAuthRequest;
import com.simplespringboot.app.dto.request.CreateBookRequestDto;
import com.simplespringboot.app.exception.CustomExceptionHandler;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.utility.Utility;
import org.apache.commons.codec.EncoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class PodService {

    private static final Logger logger = LogManager.getLogger(PodService.class);

    @Value("${pod.client.id}")
    private String podClientId;

    @Value("${pod.client.secret}")
    private String podClientSecret;

    @Value("${pod.sso.server}")
    private String podSsoServer;

    @Value("${pod.redirect.uri}")
    private String podRedirectUri;

    @Value("${pod.api.token}")
    private String podApiToken;

    @Value("${pod.code.verifier}")
    private String podCodeVerifier;

    @Value("${pod.code.challenge}")
    private String podCodeChallenge;

    public ResponseEntity<?> loginInitialize() throws OAuthException, ClientOperationException {
        KeyleadClient keyleadClient = KeyleadClientFactory.createClient(podSsoServer);
        OAuthRequest oAuthRequest = new OAuthRequest();
        oAuthRequest.setRedirect_uri(podRedirectUri);
        oAuthRequest.setClient_id(podClientId);
        oAuthRequest.setResponse_type("code");
        oAuthRequest.setScope("profile");
        String authUri = keyleadClient.getAuthorizationUrl(oAuthRequest);
        return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.CREATED,authUri));
    }
    public String login(String code) throws OAuthException, ClientOperationException, UserOperationException, EncoderException, NoSuchAlgorithmException {
        String credentials = podClientId + ":" + podClientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedCredentials);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://accounts.pod.ir/oauth2/token?grant_type=authorization_code&code="+code+"&client_id="+podClientId+"&redirect_uri="+podRedirectUri,null, String.class);
        return response.getBody();
    }

    public static String toBase64(final String clearText) throws NoSuchAlgorithmException {
        return new String(Base64.getEncoder().encode(clearText.getBytes(StandardCharsets.UTF_8)));
    }

}