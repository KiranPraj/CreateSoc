package com.icspl.createsoc.Model;

public class OwnerVPModel {
    public String coownername;
    public String coownerpancard;
    public String coownwraadharcard;
    public String coownerdob;
    public String coowneraddress;
    public String coownermobile;
    public String coownwremail;
    public String relation;

    public OwnerVPModel(String coownername, String coownerpancard, String coownwraadharcard, String coownerdob, String coowneraddress, String coownermobile, String coownwremail, String relation) {
        this.coownername = coownername;
        this.coownerpancard = coownerpancard;
        this.coownwraadharcard = coownwraadharcard;
        this.coownerdob = coownerdob;
        this.coowneraddress = coowneraddress;
        this.coownermobile = coownermobile;
        this.coownwremail = coownwremail;
        this.relation = relation;

    }

    public String getCoownername() {
        return coownername;
    }

    public void setCoownername(String coownername) {
        this.coownername = coownername;
    }

    public String getCoownerpancard() {
        return coownerpancard;
    }

    public void setCoownerpancard(String coownerpancard) {
        this.coownerpancard = coownerpancard;
    }

    public String getCoownwraadharcard() {
        return coownwraadharcard;
    }

    public void setCoownwraadharcard(String coownwraadharcard) {
        this.coownwraadharcard = coownwraadharcard;
    }

    public String getCoownerdob() {
        return coownerdob;
    }

    public void setCoownerdob(String coownerdob) {
        this.coownerdob = coownerdob;
    }

    public String getCoowneraddress() {
        return coowneraddress;
    }

    public void setCoowneraddress(String coowneraddress) {
        this.coowneraddress = coowneraddress;
    }

    public String getCoownermobile() {
        return coownermobile;
    }

    public void setCoownermobile(String coownermobile) {
        this.coownermobile = coownermobile;
    }

    public String getCoownwremail() {
        return coownwremail;
    }

    public void setCoownwremail(String coownwremail) {
        this.coownwremail = coownwremail;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
