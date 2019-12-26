package com.icspl.createsoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDetails {

    @SerializedName("SocietyId")
    @Expose
    private String societyId;
    @SerializedName("SocietyName")
    @Expose
    private String societyName;
    @SerializedName("admin_pwd")
    @Expose
    private String adminPwd;
    @SerializedName("Email")
    @Expose
    private String email;

    public String getSocietyId() {
        return societyId;
    }

    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
