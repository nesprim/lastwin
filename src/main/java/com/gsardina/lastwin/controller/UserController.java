package com.gsardina.lastwin.controller;

import com.gsardina.lastwin.model.LoginModel;
import com.gsardina.lastwin.model.ResponseModel;
import com.gsardina.lastwin.model.UserCheckedModel;
import com.gsardina.lastwin.model.UserModel;
import com.gsardina.lastwin.service.UserService;
import com.gsardina.lastwin.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseModel<UserModel> login(@RequestBody LoginModel loginModel) {
        ResponseModel<UserModel> response = new ResponseModel<>();

        try {
            UserCheckedModel userCheckedModel = userService.findByEmail(loginModel);

            if (userCheckedModel != null) {
                if (userCheckedModel.isCorrectPassword()) {
                    response.setData(userCheckedModel.getUserModel());
                    response.setEsito(MessageUtils.ESITO_OK);
                    response.setMessaggio(MessageUtils.DES_ESITO_OK);
                } else {
                    response.setEsito(MessageUtils.ESITO_KO);
                    response.setMessaggio(MessageUtils.PASSWORD_ERRATA);
                }
            } else {
                response.setEsito(MessageUtils.ESITO_KO);
                response.setMessaggio(MessageUtils.UTENTE_NON_TROVATO);
            }
        } catch(Exception e) {
            response.setEsito(MessageUtils.ESITO_KO);
            response.setMessaggio(MessageUtils.DES_ESITO_KO);
        }

        return response;
    }
}
