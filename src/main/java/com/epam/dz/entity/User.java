package com.epam.dz.entity;

import com.epam.dz.dao.Identified;

public class User implements Identified<Integer> {
    private int id;
    private String username;
    private String password;
    private String role;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public Integer getId() {
        return this.id;
    }


}