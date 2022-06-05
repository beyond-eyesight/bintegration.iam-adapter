package beyondeyesight.bintegration.access.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "realm")
public class Realm {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
}
