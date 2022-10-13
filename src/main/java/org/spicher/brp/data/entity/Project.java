package org.spicher.brp.data.entity;

public class Project {
    public Integer id;
    public String name, lead, priority,businessValue;
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

    public String getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(String businessValue) {
        this.businessValue = businessValue;
    }

    public Project(int id, String name, String lead, String priority, String businessValue) {
        this.id = id;
        this.name = name;
        this.lead = lead;
        this.priority = priority;
        this.businessValue = businessValue;

    }

}
