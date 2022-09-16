package org.spicher.brp.data.entity;

public class Feature {
    public Integer id,at,ba,dev,tm,div;
    public String project,feature,priority,value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAt() {
        return at;
    }

    public void setAt(Integer at) {
        this.at = at;
    }

    public Integer getBa() {
        return ba;
    }

    public void setBa(Integer ba) {
        this.ba = ba;
    }

    public Integer getDev() {
        return dev;
    }

    public void setDev(Integer dev) {
        this.dev = dev;
    }

    public Integer getTm() {
        return tm;
    }

    public void setTm(Integer tm) {
        this.tm = tm;
    }

    public Integer getDiv() {
        return div;
    }

    public void setDiv(Integer div) {
        this.div = div;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public Feature(int id, String project, String feature, String priority, String value, int at, int ba, int dev, int tm, int div) {
        this.id = id;
        this.project = project;
        this.feature = feature;
        this.priority = priority;
        this.value = value;
        this.at = at;
        this.ba = ba;
        this.dev = dev;
        this.tm = tm;
        this.div = div;
    }
}
