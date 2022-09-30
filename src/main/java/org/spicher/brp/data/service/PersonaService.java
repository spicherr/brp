package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Persona;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonaService {

    public static List<Persona> findAll(JdbcTemplate jdbcCustomer) {

        String sql = "select p.id, p.first_name, p.last_name, prt.role_id, prt.team_id from persona p left join persona_role_team prt on" +
                " p.id = prt.persona_id;";
        return jdbcCustomer.query(
                sql,
                (rs, rowNum) ->
                        new Persona(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getInt("role_id"),
                                rs.getInt("team_id")
                        )
        );
    }



/*

    public void update(Customer customer) {
        jdbcTemplate.update(
                "UPDATE customers SET first_name=?, last_name=? WHERE id=?",
                customer.getFirstName(), customer.getLastName(), customer.getId());
    }
    */



}
