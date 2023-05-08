package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleEntity() {
    }
}
