package com.icspl.createsoc.callback;

import com.icspl.createsoc.Model.BlockVPModel;
import com.icspl.createsoc.Model.DtCoOwnerDetailsModel;
import com.icspl.createsoc.Model.DtOwnerDetailsModel;
import com.icspl.createsoc.Model.DtSocietyProfileModel;
import com.icspl.createsoc.Model.GetFlatIdModel;
import com.icspl.createsoc.Model.GetWingIdModel;
import com.icspl.createsoc.Model.GetblockidModel;
import com.icspl.createsoc.Model.MemberDetails;
import com.icspl.createsoc.Model.NewFlatOwnerDetailModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.Model.ViewMeetingModel;
import com.icspl.createsoc.Model.ViewsocdetailsModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @GET("Login")
    Call<List<MemberDetails>>
    checklogin(@Query("lid") String username,
               @Query("lpass") String password,
               @Query("type") String type);


    @GET("CreateSociety")
    Call<List<ServerResponseModel>>
    register(@Query("societyname") String societyname,
             @Query("adminname") String adminname,
             @Query("email") String email,
             @Query("mobileno") String mobileno
    );
//    @Multipart("")
//    Call<List<ServerResponseModel>>
//    societydetail(
//            @Part("Address") String Address,
//            @Part("Address") String Registerno,
//            @Part("Address") String meterno,
//            @Part("Address") String watermeterno,
//            @Part("Address") String propertytax
//    );
    @Multipart
    @POST("AddSocietyDetails")
    Call<List<ServerResponseModel>>
    addsocietydetails(
            @Part("sid") String sid,
            @Part("sdetails") String title,
            @Part("sdesc") String description,
            @Part("sdocno") Integer docno,
            @Part MultipartBody.Part sdoc,
            @Part MultipartBody.Part simage
    );
    @Multipart
    @POST("OwnerDocuments")
    Call<List<ServerResponseModel>>
    addownerdocuments(
            @Part("fid") Integer fid,
            @Part("dname") String title,
            @Part("ddesc") String desc,
            @Part("docno") Integer docno,
            @Part MultipartBody.Part ddoc,
            @Part MultipartBody.Part dimage

    );

    /*@GET("Wings")
    Call<List<ServerResponseModel>>
    addwings(
            @Query("bid") String Bid,
            @Query("wname") String name
    );*/
    //new wing
    @GET("SetWingsDetails")
    Call<List<ServerResponseModel>>
    addwings(
            @Query("SocietyId") String sid,
            @Query("BlockId") String Bid,
            @Query("WingName") String name
    );

/*    @GET("Blocks")
    Call<List<ServerResponseModel>>
    addblocks(
            @Query("sid") Integer sid,
            @Query("bname") String blockname
    );*/
    // new block
    @GET("SetBlocks")
    Call<List<ServerResponseModel>>
    addblocks(
            @Query("SocietyId") String sid,
            @Query("BlockName") String blockname
    );

    @GET("SetFlatDetails")
    Call<List<ServerResponseModel>>
    addflats(
            @Query("SocietyId") String sid,
            @Query("BlockId") String bid,
            @Query("WingId") String wid,
            @Query("FlatNumber") String fno,
             @Query("FlatOwner") String fname,
            @Query("noofoccupent") String noofoccupant,
            @Query("buildarea") String buildarea,
            @Query("carpetarea") String carpetarea
    );

/*    @GET("GetBlockId")
    Call<List<GetblockidModel>>
    getblock();*/
//new get Block
    @GET("GetBlockDetails")
    Call<List<GetblockidModel>>
    getblock(
            @Query("SocietyId") String sid
    );

/*    @GET("GetWingsId")
    Call<List<GetWingIdModel>>
    getwings(
            @Query("bid") Integer bid
    );*/
//new Wing
    @GET("GetWingsDetails")
    Call<List<GetWingIdModel>>
    getwings(
            @Query("SocietyId") String sid,
            @Query("BlockId") String bid
    );

    @GET("GetFlatDetails")
    Call<List<GetFlatIdModel>>
    getflats(
            @Query("SocietyId") String sid,
            @Query("BlockId") String bid,
            @Query("WingId") String wid

    );
    @GET("OwnerDetails")
    Call<List<ServerResponseModel>>
    addownerdetails(
            @Query("fid") Integer fid,
            @Query("oname") String name,
            @Query("odob") String dob,
            @Query("oemail") String email,
            @Query("omobno") String mob,
            @Query("olandline") String landline,
            @Query("oadd") String address,
            @Query("oaadhar") String aadhar,
            @Query("opan") String pan,
            @Query("orelation") String relation,
            @Query("otype") String otype,
            @Query("votingright") String votingright,
            @Query("attendmeeting") String attendmeeting,
            @Query("relativename") String relativename,
            @Query("relativeno") String relativeno,
            @Query("relativelandline") String relativelandline,
            @Query("relativemail") String relativemail
    );
    @GET("ViewSocietyDetails")
    Call<List<ViewsocdetailsModel>>
    viewsocietydetails(
            @Query("sid") String sid
    );
    @GET("Notice")
    Call<List<ServerResponseModel>>
    addnotice(
            @Query("fid") int fid,
            @Query("desc") String desc
    );
    @GET("Meeting")
    Call<List<ServerResponseModel>>
    addmeeting(
            @Query("sid") String sid,
            @Query("medesc") String title,
            @Query("metype") String type,
            @Query("medate") String date
    );
    @GET("ViewMeetingDetails")
    Call<List<ViewMeetingModel>>
    getmeetingdetail(
            @Query("sid") String sid,
            @Query("Me_type") String type,
            @Query("Me_date") String date



    );
    //Change Society profile
    @GET("ViewSocietyProfile")
    Call<DtSocietyProfileModel>
    getSocietyProfile(
            @Query("S_ID") String Sid
    );
    // change it also
/*    @GET("getOwnerdetails")
    Call<DtOwnerDetailsModel>
    getOwnerdetails(
            @Query("fid") String Flat_id  // changed
    );*/
//new
@GET("getOwnerdetails")
Call<List<NewFlatOwnerDetailModel>>
getOwnerdetails(
        @Query("fid") String Flat_id  // changed
);


    @GET("getCoOwnerdetails")
    Call<DtCoOwnerDetailsModel>
    getCoOwnerDetails(
            @Query("Flat_id") String Flat_id
    );
    @GET("ownerdocuments")
    Call<DtSocietyProfileModel>
    getOwnerDocument(
            @Query("Flat_id") String Flat_id
    );


}
