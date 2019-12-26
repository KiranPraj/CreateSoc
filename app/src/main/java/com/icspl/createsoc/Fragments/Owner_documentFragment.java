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
import com.icspl.createsoc.Adaptor.GetOwnerDocumentAdaptor;
import com.icspl.createsoc.Model.DtSocietyProfileModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Owner_documentFragment extends Fragment {
    String flatid;
    RecyclerView recyclerView;
    List<DtSocietyProfileModel> list;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_owner_documents, container, false);
        recyclerView= view.findViewById(R.id.rv_owner_document);
        Common common= new Common();
        final Society_FlatActivity activity =  (Society_FlatActivity)getActivity();
        Bundle results = activity.getData();
        flatid = results.getString("MainFlatId");
        common.getAPI().getOwnerDocument(flatid).enqueue(new Callback<DtSocietyProfileModel>() {
            @Override
            public void onResponse(Call<DtSocietyProfileModel> call, Response<DtSocietyProfileModel> response) {
                if(!response.body().getDtDocuments().equals(null)&&response.isSuccessful())
                {
                    list=response.body().getDtDocuments();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL,false);
                    recyclerView.setLayoutManager(layoutManager);
                    GetOwnerDocumentAdaptor adaptor = new GetOwnerDocumentAdaptor(activity,list);
                    recyclerView.setAdapter(adaptor);

                }
                else {
                    Toast.makeText(activity, "No data", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<DtSocietyProfileModel> call, Throwable t) {
                Toast.makeText(activity, "Response error", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
