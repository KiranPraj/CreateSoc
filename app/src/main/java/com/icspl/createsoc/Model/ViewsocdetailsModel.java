package com.icspl.createsoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewsocdetailsModel {

    @SerializedName("Srno")
    @Expose
    private Integer srno;
    @SerializedName("S_id")
    @Expose
    private Integer sId;
    @SerializedName("Societyname")
    @Expose
    private String societyname;
    @SerializedName("Adminname")
    @Expose
    private String adminname;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Mobileno")
    @Expose
    private String mobileno;
    @SerializedName("S_details")
    @Expose
    private String sDetails;
    @SerializedName("S_desc")
    @Expose
    private String sDesc;
    @SerializedName("S_docs")
    @Expose
    private String sDocs;
    @SerializedName("S_image")
    @Expose
    private String sImage;
    @SerializedName("Sc_date")
    @Expose
    private String scDate;

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public Integer getSId() {
        return sId;
    }

    public void setSId(Integer sId) {
        this.sId = sId;
    }

    public String getSocietyname() {
        return societyname;
    }

    public void setSocietyname(String societyname) {
        this.societyname = societyname;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getSDetails() {
        return sDetails;
    }

    public void setSDetails(String sDetails) {
        this.sDetails = sDetails;
    }

    public String getSDesc() {
        return sDesc;
    }

    public void setSDesc(String sDesc) {
        this.sDesc = sDesc;
    }

    public String getSDocs() {
        return sDocs;
    }

    public void setSDocs(String sDocs) {
        this.sDocs = sDocs;
    }

    public String getSImage() {
        return sImage;
    }

    public void setSImage(String sImage) {
        this.sImage = sImage;
    }

    public String getScDate() {
        return scDate;
    }

    public void setScDate(String scDate) {
        this.scDate = scDate;
    }
}
