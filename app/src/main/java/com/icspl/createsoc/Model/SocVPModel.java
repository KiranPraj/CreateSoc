package com.icspl.createsoc.Model;

public class SocVPModel {
    public  String ettitle;
    public String etvalue;

    public String getEttitle() {
        return ettitle;
    }

    public void setEttitle(String ettitle) {
        this.ettitle = ettitle;
    }

    public String getEtvalue() {
        return etvalue;
    }

    public void setEtvalue(String etvalue) {
        this.etvalue = etvalue;
    }


    public SocVPModel(String ettitle, String etvalue) {
        this.ettitle = ettitle;
        this.etvalue = etvalue;
    }
}
