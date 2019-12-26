package com.icspl.createsoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFlatIdModel {
    @SerializedName("SrNo")
    @Expose
    private String srNo;
    @SerializedName("WingId")
    @Expose
    private String wingId;
    @SerializedName("FlatId")
    @Expose
    private String flatId;
    @SerializedName("FlatNo")
    @Expose
    private String flatNo;
    @SerializedName("F_CarpetArea")
    @Expose
    private String fCarpetArea;
    @SerializedName("F_BuildupArea")
    @Expose
    private String fBuildupArea;
    @SerializedName("FlatOwner")
    @Expose
    private String flatOwner;
    @SerializedName("Occupants")
    @Expose
    private String occupants;
    @SerializedName("FlatStatus")
    @Expose
    private String flatStatus;

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getWingId() {
        return wingId;
    }

    public void setWingId(String wingId) {
        this.wingId = wingId;
    }

    public String getFlatId() {
        return flatId;
    }

    public void setFlatId(String flatId) {
        this.flatId = flatId;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getFCarpetArea() {
        return fCarpetArea;
    }

    public void setFCarpetArea(String fCarpetArea) {
        this.fCarpetArea = fCarpetArea;
    }

    public String getFBuildupArea() {
        return fBuildupArea;
    }

    public void setFBuildupArea(String fBuildupArea) {
        this.fBuildupArea = fBuildupArea;
    }

    public String getFlatOwner() {
        return flatOwner;
    }

    public void setFlatOwner(String flatOwner) {
        this.flatOwner = flatOwner;
    }

    public String getOccupants() {
        return occupants;
    }

    public void setOccupants(String occupants) {
        this.occupants = occupants;
    }

    public String getFlatStatus() {
        return flatStatus;
    }

    public void setFlatStatus(String flatStatus) {
        this.flatStatus = flatStatus;
    }
}
