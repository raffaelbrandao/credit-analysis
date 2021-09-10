package com.raffaelbrandao.creditanalysis.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "privilege")
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }
}
