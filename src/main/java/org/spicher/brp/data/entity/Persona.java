package org.spicher.brp.data.entity;

public class Persona {
    private Integer id, roleId, teamId;
    private String firstName, lastName;

    public Integer getTeamId() {
        return teamId;
    }
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
    public Integer getRoleId(){
        return roleId;
    }
    public void setRoleId (int roleId) {
        this.roleId = roleId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Persona(int id, String firstName, String lastName, int roleId, int teamId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.teamId = teamId;
    }

}