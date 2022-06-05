package beyondeyesight.bintegration.access.controller;

import beyondeyesight.bintegration.access.model.Client;
import beyondeyesight.bintegration.access.model.Realm;
import beyondeyesight.bintegration.access.model.Role;
import beyondeyesight.bintegration.access.model.UserEntity;
import beyondeyesight.bintegration.access.model.dto.RoleMappingRequest;
import beyondeyesight.bintegration.access.repository.ClientJpaRepository;
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
    private final ClientJpaRepository clientJpaRepository;

    public RoleMappingController(RestTemplate restTemplate, UserEntityJpaRepository userEntityJpaRepository, RealmJpaRepository realmJpaRepository, RoleJpaRepository roleJpaRepository, ClientJpaRepository clientJpaRepository) {
        this.restTemplate = restTemplate;
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.realmJpaRepository = realmJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
    }



    @PostMapping(value = "/admin/realms/{realmName}/users/{username}/role-mappings/clients/{clientId}")
    public ResponseEntity<Object> clientLevelRoleMap(@PathVariable String realmName, @PathVariable String username, @PathVariable String clientId, @RequestHeader(value = "Authorization") String token, @RequestBody List<RoleMappingRequest> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        Realm realm = realmJpaRepository.findByName(realmName);
        UserEntity userEntity = userEntityJpaRepository.findByUsername(realm, username);

        for (RoleMappingRequest request : body) {
            //todo: role도 clientRole 필드 추가해서 검증
            if (!request.getClientRole() ) {
                throw new IllegalArgumentException("클라이언트 역할이어야 합니다.");
            }
            Role role = roleJpaRepository.findByName(realm, request.getName());
            request.setId(role.getId());
        }

        Client client = clientJpaRepository.findByClientId(realm, clientId);
        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("/admin/realms/%s/users/%s/role-mappings/clients/%s", realmName, userEntity.getId(), client.getId()), new HttpEntity<>(body, headers), Object.class);
        return response;
    }
    @PostMapping(value = "/admin/realms/{realmName}/users/{username}/role-mappings/realm")
    public ResponseEntity<Object> realmLevelRoleMap(@PathVariable String realmName, @PathVariable String username, @RequestHeader(value = "Authorization") String token, @RequestBody List<RoleMappingRequest> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        Realm realm = realmJpaRepository.findByName(realmName);
        UserEntity userEntity = userEntityJpaRepository.findByUsername(realm, username);

        for (RoleMappingRequest request : body) {
            Role role = roleJpaRepository.findByName(realm, request.getName());


            request.setId(role.getId());
        }

        ResponseEntity<Object> response = restTemplate.postForEntity(String.format("/admin/realms/%s/users/%s/role-mappings/realm", realmName, userEntity.getId()), new HttpEntity<>(body, headers), Object.class);
        return response;
    }
}


