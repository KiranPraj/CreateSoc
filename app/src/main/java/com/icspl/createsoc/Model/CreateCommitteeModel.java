package com.icspl.createsoc.Model;

public class CreateCommitteeModel {
    public String Designation;

    public CreateCommitteeModel(String designation) {
        Designation = designation;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
