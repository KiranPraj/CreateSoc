package com.icspl.createsoc.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DtOwnerDetailsModel {
    @SerializedName("dt_Owner")
    @Expose
    val dtowner: List<DtOwnerDetailsModel>? = null

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

    @SerializedName("owner_relative_otherdetails")
    @Expose
    var owner_relative_otherdetails: String? = null

    @SerializedName("owner_votingrights_with")
    @Expose
    var owner_votingrights_with: String? = null

    @SerializedName("owner_attendingmeeting_authority")
    @Expose
    var owner_attendingmeeting_authority: String? = null

    @SerializedName("owner_relativename")
    @Expose
    var owner_relativename: String? = null

    @SerializedName("owner_relativeno")
    @Expose
    var owner_relativenumber: String? = null

    @SerializedName("owner_relativel_landline")
    @Expose
    var owner_relativel_landline: String? = null

    @SerializedName("owner_relative_email")
    @Expose
    var owner_relative_email: String? = null
}
