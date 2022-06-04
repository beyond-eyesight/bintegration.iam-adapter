package beyondeyesight.bintegration.access.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class RoleController {

    private final RestTemplate restTemplate;

    public RoleController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/admin/realms/{realmName}/roles")
    public ResponseEntity<Object> createRealmRole(@RequestHeader(value = "Authorization") String token, @PathVariable String realmName, @RequestBody String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("/admin/realms/%s/roles", realmName), new HttpEntity<>(body, headers), Object.class);
        return response;

    }
}
