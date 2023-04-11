package com.gsardina.lastwin.controller;

import com.gsardina.lastwin.entity.UserEntity;
import com.gsardina.lastwin.model.*;
import com.gsardina.lastwin.service.RoleService;
import com.gsardina.lastwin.service.UserService;
import com.gsardina.lastwin.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    @PostMapping("/signin")
    public ResponseEntity<ResponseModel<JwtResponseModel>> signin(@RequestBody SigninModel signinModel) {
        ResponseModel<JwtResponseModel> response = new ResponseModel<>();

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

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseModel<?>> signup(@RequestBody SignupModel signupModel) {
        if (userService.existsByUsername(signupModel.getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseModel<>("Error: Username is already taken"));
        }

        if (userService.existsByEmail(signupModel.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseModel<>("Error: Email is already in use"));
        }

        if (signupModel.getRole().equals("USER")) {
            userService.save(new UserEntity(signupModel.getUsername(), signupModel.getEmail(), encoder.encode(signupModel.getPassword()), roleService.findByName(ERoleModel.USER)));
        } else {
            throw new RuntimeException("Error: Role is invalid");
        }

        return ResponseEntity.ok(new ResponseModel<>("User registered successfully"));
    }

}
