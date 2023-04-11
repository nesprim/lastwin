package com.gsardina.lastwin.service;

import com.gsardina.lastwin.entity.UserEntity;

public interface UserService {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    void save(UserEntity userEntity);
}
