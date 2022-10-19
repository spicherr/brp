package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.spicher.brp.data.service.PersonaService.personAlreadyExists;

@Component
public class ProjectService {

    public static List<Project> findAll(JdbcTemplate jdbc) {

        String sql = "select * from project;";
        return jdbc.query(
                sql,
                (rs, rowNum) ->
                        new Project(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("lead"),
                                rs.getString("priority"),
                                rs.getString("business_value")
                        )
        );
    }

    public static void createProject(int id, String name, int lead, String prio, String bVal, JdbcTemplate jdbc) {
            jdbc.update("INSERT INTO project (id,name,lead,priority,business_value) VALUES (?,?,?,?, ?);", id, name, lead, prio, bVal);
    }

    public static boolean projectExists(int id, JdbcTemplate jdbc){
        String sql = "select count(*) from project where id = ?;";
        Integer count = jdbc.queryForObject(sql, Integer.class, new Object[]{id} );
        if(count==0){
            return false;
        }
        return true;
    }

}
