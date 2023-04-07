package com.gsardina.lastwin.model;

import lombok.Data;

@Data
public class UserModel {

    private Long id;
    private String username;
    private String email;
    private Boolean confirmed;
}
