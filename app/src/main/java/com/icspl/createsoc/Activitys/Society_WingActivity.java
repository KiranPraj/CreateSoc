package com.icspl.createsoc.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Adaptor.Society_FlatAdapter;
import com.icspl.createsoc.Model.GetFlatIdModel;
import com.icspl.createsoc.Model.GetWingIdModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class Society_WingActivity extends AppCompatActivity implements Society_FlatAdapter.onItemFlatClick {
    TextView back_arrow, block_name;
    Spinner wing_name;
    RecyclerView Flat_names;
    Toolbar toolbar;
    List<GetWingIdModel> getWingIdModels = new ArrayList<>();
    List wing = new ArrayList();
    String block_id;
    String wings_id;
    List <GetFlatIdModel> getFlatIdModels=new ArrayList<>();
    Society_FlatAdapter society_flatAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__wing);
        back_arrow = findViewById(R.id.back_arrow);
        block_name = findViewById(R.id.block_names);
        toolbar = findViewById(R.id.toolbar_wing);
        wing_name = findViewById(R.id.spinner_wing);
        Flat_names = findViewById(R.id.recycle_flat);
        preferences= getApplicationContext().getSharedPreferences("MyPref", 0);
        editor=preferences.edit();
        setSupportActionBar(toolbar);
        back_arrow.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Common common = new Common();
        Intent i = getIntent();

       block_id = i.getStringExtra("Block_id");
       block_name.setText(i.getStringExtra("Block_name"));

        common.getAPI().getwings(preferences.getString("S_id",""),block_id).enqueue(new Callback<List<GetWingIdModel>>() {
            @Override
            public void onResponse(Call<List<GetWingIdModel>> call, Response<List<GetWingIdModel>> response) {
                if (response.body().size() > 0 && response.isSuccessful()) {
                    getWingIdModels = response.body();
                    wing.add("Select Wing");
                    for (int j = 0; j < getWingIdModels.size(); j++) {
                        if(getWingIdModels.get(j).getWingName()!=null)
                        {
                            wing.add(getWingIdModels.get(j).getWingName());
                        }
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Society_WingActivity.this, simple_spinner_item, wing);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    wing_name.setAdapter(spinnerArrayAdapter);
                    wing_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(wing_name.getSelectedItem()=="Select Wing")
                            {
                                Toast.makeText(Society_WingActivity.this, "Select Wing", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                               wings_id=getWingIdModels.get(position-1).getWingId();
                                flatdetails();

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
                else
                {
                    Toast.makeText(Society_WingActivity.this, "No Wings Availaable", Toast.LENGTH_SHORT).show();
                }

            }



            @Override
            public void onFailure(Call<List<GetWingIdModel>> call, Throwable t) {

            }
        });


    }
    private void flatdetails()
    {
        Common  common=new Common();
        common.getAPI().getflats(preferences.getString("S_id",""),block_id,wings_id).enqueue(new Callback<List<GetFlatIdModel>>() {
            @Override
            public void onResponse(Call<List<GetFlatIdModel>> call, Response<List<GetFlatIdModel>> response)
            {
                if(response.body().size()>0&&response.isSuccessful())
                {
                    getFlatIdModels=response.body();
                    GridLayoutManager  gridLayoutManager=new GridLayoutManager(Society_WingActivity.this,3);
                    Flat_names.setLayoutManager(gridLayoutManager);
                    society_flatAdapter=new Society_FlatAdapter(Society_WingActivity.this,getFlatIdModels,Society_WingActivity.this);
                    Flat_names.setAdapter(society_flatAdapter);
                }
                else
                {
                    if(getFlatIdModels.size()>0)
                    {
                        getFlatIdModels.clear();
                        society_flatAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(Society_WingActivity.this, "No Flat Available", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<GetFlatIdModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void particularFlatclick(String id) {
        Intent i=new Intent(Society_WingActivity.this,Society_FlatActivity.class);
        i.putExtra("Flatid",id);
        startActivity(i);


    }
}
