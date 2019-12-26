package com.icspl.createsoc.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.icspl.createsoc.Activitys.Society_FlatActivity;
import com.icspl.createsoc.Model.DtOwnerDetailsModel;
import com.icspl.createsoc.Model.NewFlatOwnerDetailModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlatOwnerDetailsFragment extends Fragment {

    String flatid;
    TextView owner_name,owner_pancard,owner_adharcard,owner_mobile,owner_dob,owner_address,owner_email,owner_relative_otherdetails;
    TextView owner_votingrights_with,owner_attendingmeeting_authority,owner_relativename,owner_relativenumber,owner_relativel_landline,owner_relative_email;
    List<NewFlatOwnerDetailModel> list;
    LinearLayoutCompat linear_layout_owner_details;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flat_owner_details_layout, container, false);
        linear_layout_owner_details=view.findViewById(R.id.linear_layout_owner_details);
        owner_name=view.findViewById(R.id.owner_name);
        owner_pancard=view.findViewById(R.id.owner_pancard);
        owner_adharcard=view.findViewById(R.id.owner_adharcard);
        owner_mobile=view.findViewById(R.id.owner_mobileno);
        owner_dob=view.findViewById(R.id.owner_dob);
        owner_address=view.findViewById(R.id.owner_address);
        owner_email=view.findViewById(R.id.owner_email);
        owner_relative_otherdetails=view.findViewById(R.id.owner_relative_otherdetails);
        owner_votingrights_with=view.findViewById(R.id.owner_votingrights_with);
        owner_attendingmeeting_authority=view.findViewById(R.id.owner_attendingmeeting_authority);
        owner_relativename=view.findViewById(R.id.owner_relativename);
        owner_relativenumber=view.findViewById(R.id.owner_relativenumber);
        owner_relativel_landline=view.findViewById(R.id.owner_relativel_landline);
        owner_relative_email=view.findViewById(R.id.owner_relative_email);

        Society_FlatActivity activity =  (Society_FlatActivity)getActivity();
        Bundle results = activity.getData();
        flatid = results.getString("MainFlatId");
        documentsDetails(flatid);


        return view;
    }

    private void documentsDetails(String flatid) {
        // make changes here
        Common common = new Common();
        common.getAPI().getOwnerdetails(flatid)
                .enqueue(new Callback<List<NewFlatOwnerDetailModel>>() {
                    @Override
                    public void onResponse(Call<List<NewFlatOwnerDetailModel>>call, Response<List<NewFlatOwnerDetailModel>> response) {
                        if(response.isSuccessful()&& response.body().size()>0)
                        {
                            linear_layout_owner_details.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(),"reach here",Toast.LENGTH_LONG).show();
                            list=response.body();
                            owner_name.setText(list.get(0).getFlatOwner());
                            owner_pancard.setText(list.get(0).getOPan());
                            owner_adharcard.setText(list.get(0).getOAadhar());
                            owner_mobile.setText(list.get(0).getOMobno());
                            owner_dob.setText(list.get(0).getODob());
                            owner_address.setText(list.get(0).getOAdd());
                            owner_email.setText(list.get(0).getOEmail());
                            owner_relative_otherdetails.setText("");
                            owner_votingrights_with.setText(list.get(0).getOVotingrights());
                            owner_attendingmeeting_authority.setText(list.get(0).getOAttendmeeting());
                            owner_relativename.setText(list.get(0).getORelativename());
                            owner_relativenumber.setText(list.get(0).getORelativeno());
                            owner_relativel_landline.setText(list.get(0).getORelativellno());
                            owner_relative_email.setText(list.get(0).getORelativemail());
                        }
                        else
                        {

                            linear_layout_owner_details.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<NewFlatOwnerDetailModel>> call, Throwable t) {
                        Toast.makeText(getActivity(), "No response", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
