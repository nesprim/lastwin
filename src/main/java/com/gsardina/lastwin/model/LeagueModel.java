package com.gsardina.lastwin.model;

import com.gsardina.lastwin.entity.LeagueEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueModel {

    private Long id;
    private String name;
    private Boolean prvt;
    private List<UserDetailsModel> adminList;

    public static LeagueModel build(LeagueEntity leagueEntity) {
        List<UserDetailsModel> admins = new ArrayList<>();
        leagueEntity.getAdminList().forEach(admin -> admins.add(UserDetailsModel.build(admin)));

        return new LeagueModel(
                leagueEntity.getId(),
                leagueEntity.getName(),
                leagueEntity.getPrvt(),
                admins
        );
    }
}
