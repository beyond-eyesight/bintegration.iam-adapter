package beyondeyesight.bintegration.access.controller;

import beyondeyesight.bintegration.access.model.Realm;
import beyondeyesight.bintegration.access.model.Role;
import beyondeyesight.bintegration.access.model.UserEntity;
import beyondeyesight.bintegration.access.model.dto.RoleMappingRequest;
import beyondeyesight.bintegration.access.repository.RealmJpaRepository;
import beyondeyesight.bintegration.access.repository.RoleJpaRepository;
import beyondeyesight.bintegration.access.repository.UserEntityJpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class RoleMappingController {
    private final RestTemplate restTemplate;
    private final UserEntityJpaRepository userEntityJpaRepository;
    private final RealmJpaRepository realmJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    public RoleMappingController(RestTemplate restTemplate, UserEntityJpaRepository userEntityJpaRepository, RealmJpaRepository realmJpaRepository, RoleJpaRepository roleJpaRepository) {
        this.restTemplate = restTemplate;
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.realmJpaRepository = realmJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @PostMapping(value = "/admin/realms/{realmName}/users/{username}/role-mappings/realm")
    public ResponseEntity<Object> roleMap(@PathVariable String realmName, @PathVariable String username, @RequestHeader(value = "Authorization") String token, @RequestBody List<RoleMappingRequest> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        Realm realm = realmJpaRepository.findByName(realmName);
        UserEntity userEntity = userEntityJpaRepository.findByRealmAndUsername(realm, username);

        for (RoleMappingRequest request : body) {
            Role role = roleJpaRepository.findByName(realm, request.getName());


            request.setId(role.getId());
        }

        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("/admin/realms/%s/users/%s/role-mappings/realm", realmName, userEntity.getId()), new HttpEntity<>(body, headers), Object.class);
        return response;
    }
}


