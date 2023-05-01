package com.gsardina.lastwin.service;

import com.gsardina.lastwin.entity.LeagueEntity;

public interface LeagueService {

    LeagueEntity findByName(String name);
}
