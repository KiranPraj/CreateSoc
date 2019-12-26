package com.icspl.createsoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWingIdModel {
    @SerializedName("WingId")
    @Expose
    private String wingId;
    @SerializedName("BlockId")
    @Expose
    private String blockId;
    @SerializedName("WingName")
    @Expose
    private String wingName;
    @SerializedName("BlockName")
    @Expose
    private String blockName;

    public String getWingId() {
        return wingId;
    }

    public void setWingId(String wingId) {
        this.wingId = wingId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getWingName() {
        return wingName;
    }

    public void setWingName(String wingName) {
        this.wingName = wingName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
