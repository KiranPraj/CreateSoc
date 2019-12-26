package com.icspl.createsoc.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Activitys.Society_FlatActivity;
import com.icspl.createsoc.Adaptor.CoOwnerDetailsAdaptor;
import com.icspl.createsoc.Model.DtCoOwnerDetailsModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Co_owner_Fragment extends Fragment {
    RecyclerView rv_co_owner_detail;
    String flatid;
    List<DtCoOwnerDetailsModel> list;
    CoOwnerDetailsAdaptor adaptor;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_co_owner_details, container, false);
        rv_co_owner_detail=view.findViewById(R.id.rv_Co_owner_detail);
        Common common= new Common();

        final Society_FlatActivity activity =  (Society_FlatActivity)getActivity();
        Bundle results = activity.getData();
        flatid = results.getString("MainFlatId");
        common.getAPI().getCoOwnerDetails(flatid).enqueue(new Callback<DtCoOwnerDetailsModel>() {
            @Override
            public void onResponse(Call<DtCoOwnerDetailsModel> call, Response<DtCoOwnerDetailsModel> response) {
                if(response.isSuccessful()&&!response.body().getDtcoowner().equals(null))
                {
                    list=response.body().getDtcoowner();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);
                    rv_co_owner_detail.setLayoutManager(linearLayoutManager);
                    adaptor=new CoOwnerDetailsAdaptor(activity,list);
                    rv_co_owner_detail.setAdapter(adaptor);
                }
                else {
                    Toast.makeText(activity, "No data available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DtCoOwnerDetailsModel> call, Throwable t) {
                Toast.makeText(activity, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
