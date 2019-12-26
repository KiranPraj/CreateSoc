package com.icspl.createsoc.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Model.ViewsocdetailsModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.List;

public class ViewsocActivity extends AppCompatActivity {
    public ImageView societyImage;
    public TextView tv_scname, tv_scadd, tv_adminname, tv_adminemail, tv_adminmobileno,back_arrow;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Toolbar toolbar;
    LinearLayout linear_society_details,linear_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsoc);
        societyImage = findViewById(R.id.societyImage);
        tv_scname = findViewById(R.id.tv_scname);
        tv_scadd = findViewById(R.id.tv_scadd);
        tv_adminname = findViewById(R.id.tv_adminname);
        tv_adminemail = findViewById(R.id.tv_adminemail);
        tv_adminmobileno = findViewById(R.id.tv_adminmobileno);
        linear_society_details=findViewById(R.id.linear_society_details);
        linear_profile=findViewById(R.id.linear_profile);

        toolbar=findViewById(R.id.toolbar);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        edit = pref.edit();
        toolbar.setTitle(pref.getString("Societyname",""));
        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linear_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ViewsocActivity.this,SocietyProfile.class);
                startActivity(i);
            }
        });

        linear_society_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ViewsocActivity.this,Society_BlockActivity.class);
                startActivity(i);
            }
        });

       // toolbar.setTitle("View Society");

        setSupportActionBar(toolbar);
        back_arrow.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        Common common = new Common();
         common.getAPI().viewsocietydetails(pref.getString("S_id", "")).enqueue(new Callback<List<ViewsocdetailsModel>>() {
            @Override
            public void onResponse(Call<List<ViewsocdetailsModel>> call, Response<List<ViewsocdetailsModel>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    if (response.body().get(i).getSId().equals(pref.getInt("S_id", 0))) {
                        tv_scname.setText(response.body().get(i).getSocietyname());
                        if(response.body().get(i).getSDetails().equals("Address"))
                            tv_scadd.setText(response.body().get(i).getSDesc());
                        tv_adminname.setText(response.body().get(i).getAdminname());
                        tv_adminemail.setText(response.body().get(i).getEmail());
                        tv_adminmobileno.setText(response.body().get(i).getMobileno());
                        
//                     Picasso.with(ViewsocActivity.this)
//                             .load(response.body().get(i).getSImage())
//                             .placeholder(R.drawable.loading)
//
//                               .into(societyImage);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<ViewsocdetailsModel>> call, Throwable t) {
                Toast.makeText(ViewsocActivity.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
