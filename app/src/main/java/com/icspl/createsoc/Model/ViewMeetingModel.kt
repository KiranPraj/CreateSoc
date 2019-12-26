package com.icspl.createsoc.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ViewMeetingModel
{

    @SerializedName("me_id")
    @Expose
    public lateinit var meId:String;
    @SerializedName("S_id")
    @Expose
    public  lateinit var sId:String;
    @SerializedName("Me_desc")
    @Expose
    public lateinit var meDesc:String;
    @SerializedName("Me_type")
    @Expose
    public  lateinit var meType:String;
    @SerializedName("Me_date")
    @Expose
    public  lateinit var me_date:String;
    @SerializedName("Mec_date")
    @Expose
    public  lateinit var mec_date:String;
    
}


