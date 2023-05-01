package com.gsardina.lastwin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "leagues", schema = "lastwin")
public class LeagueEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "prvt")
    private Boolean prvt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin_league", schema = "lastwin",
            joinColumns = @JoinColumn(name = "id_league"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<UserEntity> adminList;
}
