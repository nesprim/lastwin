package com.gsardina.lastwin.service.impl;

import com.gsardina.lastwin.entity.LeagueEntity;
import com.gsardina.lastwin.repository.LeagueRepository;
import com.gsardina.lastwin.service.LeagueService;
import com.gsardina.lastwin.utils.MessageUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    @Override
    public LeagueEntity findByName(String name) {
        Optional<LeagueEntity> league = leagueRepository.findByName(name);

        return league.orElseThrow(() -> new ObjectNotFoundException(LeagueEntity.class.getName(), league));
    }
}
