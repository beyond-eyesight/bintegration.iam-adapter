package beyondeyesight.bintegration.access.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class KeycloakClientConfig {

    @Value("${keycloak.auth-server-url}")
    private String baseUrl;

    @Bean("keycloakClient")
    public RestTemplate keycloakClient() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
        return restTemplate;
    }
}
