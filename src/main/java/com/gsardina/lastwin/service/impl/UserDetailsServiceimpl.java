package com.gsardina.lastwin.service.impl;

import com.gsardina.lastwin.entity.UserEntity;
import com.gsardina.lastwin.model.UserDetailsModel;
import com.gsardina.lastwin.repository.UserRepository;
import com.gsardina.lastwin.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageUtils.USER_NOT_FOUND_MESSAGE + username));

        return UserDetailsModel.build(userEntity);
    }
}
