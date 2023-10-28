package com.sportyfind.webapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportyfind.webapi.dtos.AuthCreateReqDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_team",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id")})
    private List<TeamEntity> teams;

    private String email;
    private String fullName;
    private String gender;
    private Date birthday;
    private String phoneNumber;
    private String address;
    private Double height;
    private Double weight;
    private String thumbnail;

    public UserEntity() {
        this.roles = List.of(new RoleEntity(1L, "ROLE_USER"));
    }
}