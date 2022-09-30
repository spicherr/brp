package org.spicher.brp.data.entity;

public class Project {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public Integer id;
    public String name, lead, priority,value;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean active;

    public Project(int id, String name, String lead, String priority, String value, Boolean active) {
        this.id = id;
        this.name = name;
        this.lead = lead;
        this.priority = priority;
        this.value = value;
        this.active = active;
    }

}
