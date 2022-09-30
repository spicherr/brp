package org.spicher.brp.data.entity;

public class PersonaRoleTeam {
    Integer id, personaId, roleId, teamId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public PersonaRoleTeam(int id, int personaId, int roleId, int teamId) {
        this.id = id;
        this.personaId = personaId;
        this.roleId = roleId;
        this.teamId = teamId;
    }
}
