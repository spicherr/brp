package org.spicher.brp.data.entity;

public class Role {
    Integer id;
    static String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Role(int id, String name){
        this.id = id;
        this.name = name;
    }


}
