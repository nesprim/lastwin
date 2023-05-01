package com.gsardina.lastwin.repository;

import com.gsardina.lastwin.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {

    Optional<LeagueEntity> findByName(String name);
}
