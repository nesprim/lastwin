package com.gsardina.lastwin.controller;

import com.gsardina.lastwin.entity.UserEntity;
import com.gsardina.lastwin.model.*;
import com.gsardina.lastwin.service.RoleService;
import com.gsardina.lastwin.service.UserService;
import com.gsardina.lastwin.utils.JwtUtils;
import com.gsardina.lastwin.utils.MailUtils;
import com.gsardina.lastwin.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    MailUtils mailUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseModel<JwtResponseModel> signin(@RequestBody SigninModel signinModel) {
        ResponseModel<JwtResponseModel> response = new ResponseModel<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinModel.getUsername(), signinModel.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();

            if (userDetails.getConfirmed()) {
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList();

                response.setData(new JwtResponseModel(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        true,
                        roles
                ));

                response.setEsito(MessageUtils.OK);
                response.setMessage(MessageUtils.SIGNIN_SUCCESSFUL);
            } else {
                response.setData(new JwtResponseModel(
                        null,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        false,
                        null
                ));

                UserEntity userEntity = userService.findByUsername(signinModel.getUsername());
                mailUtils.sendRegistrationMail(userEntity.getUsername(), userEntity.getEmail(), userEntity.getConfirmCode());

                response.setEsito(MessageUtils.KO);
                response.setMessage(MessageUtils.ACCOUNT_NOT_CONFIRMED);
            }
        } catch (BadCredentialsException e) {
            response = new ResponseModel<>(MessageUtils.KO, MessageUtils.BAD_CREDENTIALS);

            logger.error(e.getMessage());
        }

        return response;
    }

    @PostMapping("/confirm")
    public ResponseModel<?> confirm(@RequestBody ConfirmEmailModel confirmEmailModel) {
        ResponseModel<?> response;

        try {
            if (confirmEmailModel.getConfirmCode().equals(userService.findByUsername(confirmEmailModel.getUsername()).getConfirmCode())) {
                userService.confirmAccount(confirmEmailModel.getUsername());

                response = new ResponseModel<>(MessageUtils.OK, MessageUtils.ACCOUNT_CONFIRMED);
            } else {
                response = new ResponseModel<>(MessageUtils.KO, MessageUtils.WRONG_CONFIRM_CODE);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(MessageUtils.KO, MessageUtils.MESSAGE_KO);

            logger.error(e.getMessage());
        }

        return response;
    }

    @PostMapping("/signup")
    public ResponseModel<?> signup(@RequestBody SignupModel signupModel) {
        ResponseModel<?> response;

        try {
            if (ERoleModel.USER.name().equals(signupModel.getRole())) {
                if (userService.existsByUsername(signupModel.getUsername())) {
                    response = new ResponseModel<>(MessageUtils.KO, MessageUtils.USERNAME_UNAVAILABLE);
                } else if (userService.existsByEmail(signupModel.getEmail())) {
                    response = new ResponseModel<>(MessageUtils.KO, MessageUtils.EMAIL_ALREADY_USED);
                } else {
                    String confirmCode = userService.signup(new UserEntity(signupModel.getUsername(), signupModel.getEmail(), encoder.encode(signupModel.getPassword()), MailUtils.generateConfirmCode(), roleService.findByName(ERoleModel.USER)));
                    mailUtils.sendRegistrationMail(signupModel.getUsername(), signupModel.getEmail(), confirmCode);

                    response = new ResponseModel<>(MessageUtils.OK, MessageUtils.SIGNUP_SUCCESSFUL);
                }
            } else {
                response = new ResponseModel<>(MessageUtils.KO, MessageUtils.INVALID_ROLE);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(MessageUtils.KO, MessageUtils.MESSAGE_KO);

            logger.error(e.getMessage());
        }

        return response;
    }

    @GetMapping("/confirm")
    public ResponseModel<?> confirm(@RequestParam("username") String username, @RequestParam("confirmCode") String confirmCode) {
        ResponseModel<?> response;
        try {
            UserEntity userEntity = userService.findByUsername(username);

            if (confirmCode != null && confirmCode.equals(userEntity.getConfirmCode())) {
                if (userEntity.getConfirmed()) {
                    response = new ResponseModel<>(MessageUtils.KO, MessageUtils.ACCOUNT_ALREADY_CONFIRMED);
                } else {
                    userService.confirmAccount(username);

                    response = new ResponseModel<>(MessageUtils.OK, MessageUtils.ACCOUNT_CONFIRMED);
                }
            } else {
                response = new ResponseModel<>(MessageUtils.KO, MessageUtils.INVALID_CONFIRM_CODE);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(MessageUtils.KO, MessageUtils.INVALID_USERNAME);

            logger.error(e.getMessage());
        }

        return response;
    }

}
