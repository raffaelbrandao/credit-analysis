package com.raffaelbrandao.creditanalysis.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public Collection<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
