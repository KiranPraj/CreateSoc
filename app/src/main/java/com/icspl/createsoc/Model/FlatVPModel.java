package com.icspl.createsoc.Model;

public class FlatVPModel {
    public String flatname;
    public String flatno;

    public FlatVPModel(String flatname, String flatno) {
        this.flatname = flatname;
        this.flatno = flatno;
    }

    public String getFlatname() {
        return flatname;
    }

    public void setFlatname(String flatname) {
        this.flatname = flatname;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }
}
