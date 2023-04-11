package com.gsardina.lastwin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponseModel {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Boolean isConfirmed;
    private List<String> roles;

    public JwtResponseModel(String accessToken, Long id, String username, String email, Boolean isConfirmed, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.isConfirmed = isConfirmed;
        this.roles = roles;
    }
}
