package beyondeyesight.bintegration.access.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class TokenController {

    private final RestTemplate restTemplate;

    public TokenController(@Qualifier("keycloakClient") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/realms/{realmName}/protocol/openid-connect/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Object> accessToken(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable String realmName, @RequestBody String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("/realms/%s/protocol/openid-connect/token", realmName), new HttpEntity<>(body, headers), Object.class);
        return response;
    }
}



