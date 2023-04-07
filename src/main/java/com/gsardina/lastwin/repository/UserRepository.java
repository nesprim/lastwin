package com.gsardina.lastwin.repository;

import com.gsardina.lastwin.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("select u from UserEntity u where u.email = :email")
    UserEntity findByEmail(@Param("email") String email);

}
