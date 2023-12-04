package com.jm.retroguias.model;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private String id; // email
    private String name;
    private String last_name;
    private int phone;
    private boolean isMaster;
    private List<String> fav;

    public Users() {
    }

    public Users(String id, String name, String last_name, int phone)
    {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.phone = phone;
        this.isMaster = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public List<String> getFav() {
        return fav;
    }

    public void setFav(List<String> fav) {
        this.fav = fav;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone=" + phone +
                ", isMaster=" + isMaster +
                ", fav=" + fav +
                '}';
    }
}
