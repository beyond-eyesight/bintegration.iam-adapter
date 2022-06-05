package beyondeyesight.bintegration.access.repository;

import beyondeyesight.bintegration.access.model.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RealmJpaRepository extends JpaRepository<Realm, String> {
    @Query("select r from Realm  r where  r.name = :name")
    Realm findByName(String name);
}
