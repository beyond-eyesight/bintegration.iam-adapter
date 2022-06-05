package beyondeyesight.bintegration.access.repository;

import beyondeyesight.bintegration.access.model.Client;
import beyondeyesight.bintegration.access.model.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientJpaRepository extends JpaRepository<Client, String> {

    @Query("select c from Client c where c.realm = :realm and c.clientId = :clientId")
    Client findByClientId(Realm realm, String clientId);
}
