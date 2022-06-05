package beyondeyesight.bintegration.access.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "keycloak_role")
@Getter
public class Role {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;



}
