package org.spicher.brp.data.service;

import org.spicher.brp.data.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleService {
    public static List<Role> getAllActive(JdbcTemplate jdbc) {

        String sql = "select id, name from role where status = 1;";
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
    public static void inactivateRole(int roleId, JdbcTemplate jdbc) {
        jdbc.update(
                "UPDATE role set status=0 where id = ?;",
                roleId
        );
    }
}
