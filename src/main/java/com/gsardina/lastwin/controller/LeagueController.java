package com.gsardina.lastwin.controller;

import com.gsardina.lastwin.model.LeagueModel;
import com.gsardina.lastwin.model.ResponseModel;
import com.gsardina.lastwin.service.LeagueService;
import com.gsardina.lastwin.utils.MessageUtils;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/league")
public class LeagueController  {

    @Autowired
    LeagueService leagueService;

    private static final Logger logger = LoggerFactory.getLogger(LeagueController.class);

    @GetMapping("/findByName/{name}")
    public ResponseModel<LeagueModel> findByName(@PathVariable(name = "name") String name) {
        ResponseModel<LeagueModel> response = new ResponseModel<>();

        try {
            response.setEsito(MessageUtils.OK);
            response.setMessage(MessageUtils.MESSAGE_OK);
            response.setData(LeagueModel.build(leagueService.findByName(name)));
        } catch (ObjectNotFoundException e) {
            response.setEsito(MessageUtils.KO);
            response.setMessage(e.getMessage());

            logger.error(e.getMessage());
        }

        return response;
    }
}
