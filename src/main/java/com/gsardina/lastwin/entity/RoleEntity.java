package com.gsardina.lastwin.entity;

import com.gsardina.lastwin.model.ERoleModel;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "roles", schema = "lastwin")
public class RoleEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERoleModel name;
}
