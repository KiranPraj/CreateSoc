package com.icspl.createsoc.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Adaptor.Society_BlockAdapter;
import com.icspl.createsoc.Model.GetblockidModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Society_BlockActivity extends AppCompatActivity implements Society_BlockAdapter.AdpterOnItemClick {
    TextView back_arrow;
    RecyclerView block_recyc;
    Toolbar toolbar;
    List<GetblockidModel> getblockidModelList = new ArrayList<>();
    Society_BlockAdapter society_blockAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__block);
        back_arrow = findViewById(R.id.back_arrow);
        block_recyc = findViewById(R.id.society_blocks);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        back_arrow.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        preferences= getApplicationContext().getSharedPreferences("MyPref", 0);
        editor=preferences.edit();

        Common common = new Common();
        common.getAPI().getblock(preferences.getString("S_id","")).enqueue(new Callback<List<GetblockidModel>>() {
            @Override
            public void onResponse(Call<List<GetblockidModel>> call, Response<List<GetblockidModel>> response) {
                if (response.body().size() > 0 && response.isSuccessful()) {
                    getblockidModelList = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    block_recyc.setLayoutManager(linearLayoutManager);
                    List<String> list= new ArrayList<>();
                    for (int i = 0; i <getblockidModelList.size() ; i++) {
                        list.add(getblockidModelList.get(i).getBlockName());
                    }
                    society_blockAdapter = new Society_BlockAdapter(getApplicationContext(), getblockidModelList, Society_BlockActivity.this);
                    block_recyc.setAdapter(society_blockAdapter);

                }


            }

            @Override
            public void onFailure(Call<List<GetblockidModel>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(String id,String name) {

        Intent i = new Intent(Society_BlockActivity.this, Society_WingActivity.class);
        i.putExtra("Block_id", id);
        i.putExtra("Block_name",name);
        startActivity(i);

    }
}
