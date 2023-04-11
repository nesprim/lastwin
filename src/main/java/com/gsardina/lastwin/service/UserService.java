package com.gsardina.lastwin.service;

import com.gsardina.lastwin.entity.UserEntity;

public interface UserService {

    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    Boolean existsByEmail(String email);
    String signup(UserEntity userEntity);
    void confirmAccount(String username);
}
