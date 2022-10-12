package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleService {
    public static List<Role> getAll(JdbcTemplate jdbc) {

        String sql = "select id, name from role;";
        return jdbc.query(
                sql,
                (rs, rowNum) -> new Role(
                        rs.getInt("id"),
                        rs.getString("name")
                )
        );
    }

    public static void addRole(String roleName, JdbcTemplate jdbc) {
        jdbc.update(
                "INSERT INTO role (name) VALUES ( ?);",
                roleName
        );
    }
}
