package com.gsardina.lastwin.transfer;

import com.gsardina.lastwin.entity.UserEntity;
import com.gsardina.lastwin.model.UserModel;

public class UserTransfer {

    public static UserModel entityToModel(UserEntity entity) {
        UserModel userModel = new UserModel();

        userModel.setId(entity.getId());
        userModel.setUsername(entity.getUsername());
        userModel.setEmail(entity.getEmail());
        userModel.setConfirmed(entity.getConfirmed());

        return userModel;
    }
}
