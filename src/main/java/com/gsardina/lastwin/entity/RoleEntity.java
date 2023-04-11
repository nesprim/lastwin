package com.gsardina.lastwin.entity;

import com.gsardina.lastwin.model.ERoleModel;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ROLES", schema = "L_S")
public class RoleEntity implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private ERoleModel name;
}
