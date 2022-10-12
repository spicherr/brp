package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.PersonaRoleTeam;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PersonaRoleTeamService {

    public static List<PersonaRoleTeam> getAll(JdbcTemplate jdbc) {

        String sql = "select * from persona_role_team;";
        return jdbc.query(
                sql,
                (rs, rowNum) -> new PersonaRoleTeam(
                        rs.getInt("id"),
                        rs.getInt("personaId"),
                        rs.getInt("roleId"),
                        rs.getInt("teamId")
                )
        );

    }

    public static void addRelation(String lastName, String firstName, int roleId, int teamId, JdbcTemplate jdbc) {
        jdbc.update(
                "INSERT INTO persona_role_team (persona_id, role_id, team_id) VALUES ((select id from persona where lastName = ? and " +
                        "firstName = ?),?,?);",
                lastName, firstName, roleId,teamId
        );
    }
}
