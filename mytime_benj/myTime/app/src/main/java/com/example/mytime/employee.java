package com.example.mytime;

import java.io.Serializable;

public class employee implements Serializable {

    int id;
    int branchID;
    String name;
    String username;
    String password;

    public employee(int branchID,String name, String username, String password) {
        this.branchID=branchID;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public employee(int id,int branchID,String name, String username, String password) {
        this.id = id;
        this.branchID=branchID;
        this.name = name;
        this.username = username;
        this.password = password;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
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




    @Override
    public String toString() {
        return name;
    }

}
