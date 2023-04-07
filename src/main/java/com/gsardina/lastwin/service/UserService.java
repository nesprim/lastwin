package com.gsardina.lastwin.service;

import com.gsardina.lastwin.model.LoginModel;
import com.gsardina.lastwin.model.UserCheckedModel;

public interface UserService {

    UserCheckedModel findByEmail(LoginModel loginModel);
}
