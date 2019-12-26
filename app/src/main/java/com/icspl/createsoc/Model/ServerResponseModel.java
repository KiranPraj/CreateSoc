package com.icspl.createsoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponseModel {
    @SerializedName("ServerResponse")
    @Expose
    private Integer serverResponse;

    public Integer getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(Integer serverResponse) {
        this.serverResponse = serverResponse;
    }

}
