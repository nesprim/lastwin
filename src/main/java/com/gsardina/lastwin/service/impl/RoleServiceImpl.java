package com.gsardina.lastwin.service.impl;

import com.gsardina.lastwin.entity.RoleEntity;
import com.gsardina.lastwin.model.ERoleModel;
import com.gsardina.lastwin.repository.RoleRepository;
import com.gsardina.lastwin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(ERoleModel name) {
        return roleRepository.findByName(name).orElseThrow();
    }
}
