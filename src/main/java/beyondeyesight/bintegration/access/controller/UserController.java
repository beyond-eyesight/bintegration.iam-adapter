package beyondeyesight.bintegration.access.controller;

import beyondeyesight.bintegration.access.model.Realm;
import beyondeyesight.bintegration.access.model.UserEntity;
import beyondeyesight.bintegration.access.repository.RealmJpaRepository;
import beyondeyesight.bintegration.access.repository.UserEntityJpaRepository;
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

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final RealmJpaRepository realmJpaRepository;

    public UserController(@Qualifier("keycloakClient") RestTemplate restTemplate, UserEntityJpaRepository userEntityJpaRepository, RealmJpaRepository realmJpaRepository) {
        this.restTemplate = restTemplate;
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.realmJpaRepository = realmJpaRepository;
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


    @GetMapping(value = "/admin/realms/{realmName}/users")
    public ResponseEntity<Object> findUserByUsername(@PathVariable String realmName, @RequestParam(value = "username") String username) {
        Realm realm = realmJpaRepository.findByName(realmName);
        UserEntity userEntity = userEntityJpaRepository.findByUsername(realm, username);
        return ResponseEntity.ok(userEntity);
    }


}
