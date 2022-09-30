package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService {

    public static List<Project> findAll(JdbcTemplate jdbProject) {

        String sql = "select * from project;";
        return jdbProject.query(
                sql,
                (rs, rowNum) ->
                        new Project(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("lead"),
                                rs.getString("priority"),
                                rs.getString("value"),
                                rs.getBoolean("isActive")
                        )
        );
    }

}
