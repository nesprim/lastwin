package com.gsardina.lastwin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users", schema = "lastwin")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Column(name = "confirm_code")
    private String confirmCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles", schema = "lastwin",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
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
