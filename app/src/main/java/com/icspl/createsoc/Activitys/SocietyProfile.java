package com.icspl.createsoc.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.icspl.createsoc.Adaptor.Society_ProfileAdapter;
import com.icspl.createsoc.Model.DtSocietyProfileModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocietyProfile extends AppCompatActivity {
    Toolbar toolbar2;
    TextView back_arrow;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;
    List<DtSocietyProfileModel> list= new ArrayList<>();
    Society_ProfileAdapter society_profileAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__profile);
        rv=findViewById(R.id.society_document_title);
        pref= getApplicationContext().getSharedPreferences("MyPref",0);
        edit=pref.edit();
        back_arrow=findViewById(R.id.back_arrow);
        toolbar2=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        back_arrow.setText(toolbar2.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Common common= new Common();
        common.getAPI().getSocietyProfile(pref.getString("S_id",""))
                .enqueue(new Callback<DtSocietyProfileModel>() {
                    @Override
                    public void onResponse(Call<DtSocietyProfileModel> call, Response<DtSocietyProfileModel> response) {
                        if(response.isSuccessful()&&response.body().getDtDocuments().size()>0)
                        {
                            list=response.body().getDtDocuments();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SocietyProfile.this, RecyclerView.VERTICAL, false);
                            rv.setLayoutManager(linearLayoutManager);
                            society_profileAdapter = new Society_ProfileAdapter(SocietyProfile.this, list);
                            rv.setAdapter(society_profileAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<DtSocietyProfileModel> call, Throwable t) {

                    }
                });
    }
}