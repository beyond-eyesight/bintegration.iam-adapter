package beyondeyesight.bintegration.access.repository;

import beyondeyesight.bintegration.access.model.Realm;
import beyondeyesight.bintegration.access.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserEntityJpaRepository extends JpaRepository<UserEntity, String> {
    @Query("select u from UserEntity u where u.realm = :realm and u.userName = :username")
    UserEntity findByRealmAndUsername(Realm realm, String username);
}
