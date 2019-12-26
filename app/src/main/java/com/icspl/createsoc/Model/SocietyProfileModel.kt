package com.icspl.createsoc.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class DtSocietyProfileModel{

    @SerializedName("dt_Location")
    @Expose
    val dtDocuments: List<DtSocietyProfileModel>? = null
    @SerializedName("Title")
    @Expose
    val srNo: String? = null
    @SerializedName("Description")
    @Expose
    val location: String? = null
    @SerializedName("File")
    @Expose
    val storeName: String? = null
    @SerializedName("photo")
    @Expose
    val storeAddress: String? = null
}