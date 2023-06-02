package com.sportyfind.webapi.entities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "owner")
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerid")
    public Integer ownerId;

    @Column(name = "phone")
    public String phone;

    @Column(name = "ownername")
    public String ownerName;
}

