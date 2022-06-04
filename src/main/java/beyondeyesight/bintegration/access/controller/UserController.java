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
public class UserController {

    private final RestTemplate restTemplate;

    public UserController(@Qualifier("keycloakClient") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostMapping(value = "/admin/realms/{realmName}/users")
    public ResponseEntity<Object> createUser(@RequestHeader(value = "Authorization") String token, @PathVariable String realmName, @RequestBody String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("/admin/realms/%s/users", realmName), new HttpEntity<>(body, headers), Object.class);
        return response;
    }

}
