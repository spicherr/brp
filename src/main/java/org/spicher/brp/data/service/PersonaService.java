package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Persona;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonaService {

    public static List<Persona> findAll(JdbcTemplate jdbc) {

        String sql = "select p.id, p.firstname, p.lastname, prt.role_id, prt.team_id from persona p left join persona_role_team prt on" +
                " p.id = prt.persona_id;";
        return jdbc.query(
                sql,
                (rs, rowNum) ->
                        new Persona(
                                rs.getInt("id"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getInt("role_id"),
                                rs.getInt("team_id")
                        )
        );
    }

    public static void createNewPerson(String firstName, String lastName,  JdbcTemplate jdbc) {

        jdbc.update(
                "INSERT INTO persona (firstname, lastname) VALUES (?, ?);",
                firstName, lastName
        );
    }
    public static void createPersonRoleTeam(Integer personId, Integer roleId, Integer teamId, JdbcTemplate jdbc){
        jdbc.update(
                "INSERT INTO persona_role_team (personId, roleId, teamId,) VALUES (?, ?,?);",
                personId, roleId, teamId
        );

    }

    public static void removePerson(Persona person, JdbcTemplate jdbc) {
    }

    public static void editPerson(Persona person, JdbcTemplate jdbc) {

    }



/*

    public void update(Customer customer) {
        jdbcTemplate.update(
                "UPDATE customers SET first_name=?, last_name=? WHERE id=?",
                customer.getFirstName(), customer.getLastName(), customer.getId());
    }
    */



}
