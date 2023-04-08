package com.gsardina.lastwin.service;

import com.gsardina.lastwin.model.LoginModel;
import com.gsardina.lastwin.model.RegisterModel;
import com.gsardina.lastwin.model.UserCheckedModel;
import com.gsardina.lastwin.model.UserModel;

public interface UserService {

    UserCheckedModel findByEmail(LoginModel loginModel);
    UserModel register(RegisterModel registerModel);
}
