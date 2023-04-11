package com.gsardina.lastwin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "USERS", schema = "L_S")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CONFIRMED")
    private Boolean confirmed;

    @Column(name = "CONFIRM_CODE")
    private String confirmCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_ROLES", schema = "L_S",
            joinColumns = @JoinColumn(name = "ID_USER"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    private RoleEntity role;

    public UserEntity(String username, String email, String password, String confirmCode, RoleEntity role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmed = false;
        this.confirmCode = confirmCode;
        this.role = role;
    }

}
