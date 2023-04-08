package com.gsardina.lastwin.service.impl;

import com.gsardina.lastwin.entity.UserEntity;
import com.gsardina.lastwin.model.LoginModel;
import com.gsardina.lastwin.model.RegisterModel;
import com.gsardina.lastwin.model.UserCheckedModel;
import com.gsardina.lastwin.model.UserModel;
import com.gsardina.lastwin.repository.UserRepository;
import com.gsardina.lastwin.service.UserService;
import com.gsardina.lastwin.transfer.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserCheckedModel findByEmail(LoginModel loginModel) {
        UserCheckedModel userCheckedModel = new UserCheckedModel();
        UserEntity userEntity = userRepository.findByEmail(loginModel.getEmail());

        if (userEntity != null) {
            userCheckedModel.setUserModel(UserTransfer.entityToModel(userEntity));
            //verificare con encoder di password
            userCheckedModel.setCorrectPassword(userEntity.getPassword().equals(loginModel.getPassword()));

            return userCheckedModel;
        } else {
            return null;
        }
    }

    @Override
    public UserModel register(RegisterModel registerModel) {
        UserEntity userEntity = new UserEntity();

        if (userRepository.findByEmail(registerModel.getEmail()) == null) {
            userEntity.setEmail(registerModel.getEmail());

            if (userRepository.findByUsername(registerModel.getUsername()) == null) {
                userEntity.fillRegisterInformation(registerModel);
                userRepository.save(userEntity);
            }
        }

        return UserTransfer.entityToModel(userEntity);
    }
}
