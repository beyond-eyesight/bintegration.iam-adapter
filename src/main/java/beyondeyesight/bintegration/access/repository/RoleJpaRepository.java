package beyondeyesight.bintegration.access.repository;

import beyondeyesight.bintegration.access.model.Realm;
import beyondeyesight.bintegration.access.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleJpaRepository extends JpaRepository<Role, String> {

    @Query("select r from  Role  r where r.realm = :realm and r.name = :name")
    Role findByName(Realm realm, String name);

}
