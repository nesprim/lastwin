package com.gsardina.lastwin.repository;

import com.gsardina.lastwin.entity.RoleEntity;
import com.gsardina.lastwin.model.ERoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERoleModel role);
}
