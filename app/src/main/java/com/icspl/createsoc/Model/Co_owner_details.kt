package com.icspl.createsoc.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DtCoOwnerDetailsModel {
    @SerializedName("dt_coowner")
    @Expose
    val dtcoowner: List<DtCoOwnerDetailsModel>? = null

    @SerializedName("owner_name")
    @Expose
     var owner_name: String? = null

    @SerializedName("owner_pancard")
    @Expose
     var owner_pancard: String? = null

    @SerializedName("owner_adharcard")
    @Expose
     var owner_adharcard: String? = null

    @SerializedName("owner_mobile")
    @Expose
     var owner_mobile: String? = null

    @SerializedName("owner_dob")
    @Expose
     var owner_dob: String? = null

    @SerializedName("owner_address")
    @Expose
     var owner_address: String? = null

    @SerializedName("owner_email")
    @Expose
     var owner_email: String? = null
}