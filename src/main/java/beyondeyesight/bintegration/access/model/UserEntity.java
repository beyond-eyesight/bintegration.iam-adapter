package beyondeyesight.bintegration.access.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "user_entity")
@Getter
public class UserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;

}
