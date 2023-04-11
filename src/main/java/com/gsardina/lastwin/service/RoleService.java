package com.gsardina.lastwin.service;

import com.gsardina.lastwin.entity.RoleEntity;
import com.gsardina.lastwin.model.ERoleModel;

public interface RoleService {

    RoleEntity findByName(ERoleModel name);
}
