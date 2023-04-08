package com.gsardina.lastwin.entity;

import com.gsardina.lastwin.model.RegisterModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users", schema = "l_s")
public class UserEntity implements Serializable {

    @Id
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

    public void fillRegisterInformation(RegisterModel registerModel) {
        this.username = registerModel.getUsername();
        //salvare password codificata
        this.password = registerModel.getPassword();
        this.confirmed = false;
    }
}
