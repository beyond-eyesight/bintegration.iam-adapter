package beyondeyesight.bintegration.access.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Getter
public class Client {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;
}
