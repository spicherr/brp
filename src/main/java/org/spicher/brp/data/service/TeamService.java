package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Team;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TeamService {
    public static List<Team> getAll(JdbcTemplate jdbc) {

        String sql = "select id, name from team;";
        return jdbc.query(
                sql,
                (rs, rowNum) -> new Team(
                        rs.getInt("id"),
                        rs.getString("name")
                )
        );
    }
}