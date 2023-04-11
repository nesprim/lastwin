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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseModel<JwtResponseModel>> signin(@RequestBody SigninModel signinModel) {
        ResponseModel<JwtResponseModel> response = new ResponseModel<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinModel.getUsername(), signinModel.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList();

            response.setData(new JwtResponseModel(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getConfirmed(),
                    roles));

            response.setMessage(MessageUtils.SIGNIN_SUCCESSFUL);
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());

            return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.BAD_CREDENTIALS));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseModel<?>> signup(@RequestBody SignupModel signupModel) {
        if (userService.existsByUsername(signupModel.getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.USERNAME_UNAVAILABLE));
        }

        if (userService.existsByEmail(signupModel.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.EMAIL_ALREADY_USED));
        }

        if (signupModel.getRole().equals(ERoleModel.USER.name())) {
            String confirmCode = userService.signup(new UserEntity(signupModel.getUsername(), signupModel.getEmail(), encoder.encode(signupModel.getPassword()), MailUtils.generateConfirmCode(), roleService.findByName(ERoleModel.USER)));

            mailUtils.sendRegistrationMail(signupModel.getUsername(), signupModel.getEmail(), confirmCode);
        } else {
            return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.INVALID_ROLE));
        }

        return ResponseEntity.ok(new ResponseModel<>(MessageUtils.SIGNUP_SUCCESSFUL));
    }

    @GetMapping("/confirm")
    public ResponseEntity<ResponseModel<?>> confirm(@RequestParam("username") String username, @RequestParam("confirmCode") String confirmCode) {
        try {
            UserEntity userEntity = userService.findByUsername(username);

            if (confirmCode != null && confirmCode.equals(userEntity.getConfirmCode())) {
                if (userEntity.getConfirmed()) {
                    return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.ACCOUNT_ALREADY_CONFIRMED));
                } else {
                    userService.confirmAccount(username);
                }
            } else {
                return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.INVALID_CONFIRM_CODE));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity.badRequest().body(new ResponseModel<>(MessageUtils.INVALID_USERNAME));
        }

        return ResponseEntity.ok(new ResponseModel<>(MessageUtils.ACCOUNT_CONFIRMED));
    }

}
