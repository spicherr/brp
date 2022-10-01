package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleService {

    public static List<String> getAll(JdbcTemplate jdbc) {

        String sql = "select name from role;";
        return jdbc.query(
                sql,
                (rs, rowNum) ->
                        Role.getName()
                        );

    }
}
