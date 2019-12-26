package com.icspl.createsoc.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.CreateMeetingAdaptor;
import com.icspl.createsoc.Model.CreateMeetingModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateMeeting extends AppCompatActivity implements CreateMeetingAdaptor.Meetinghandler {
    public RecyclerView rv_genmeeting, rv_spetialmeeting;
    public Button btn_generalsave, btn_spetialsave;
    public EditText et_nogeneral, et_nospetial;
    public Spinner meetingtype;
    public CreateMeetingAdaptor createMeetingAdaptor;
    public List<CreateMeetingModel> list;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;
    private HashMap<Integer ,String> meetingtitle = new HashMap<>();
    private HashMap<Integer ,String> meetingdate = new HashMap<>();
    public Context mContext;
    Toolbar toolbar_meeting;
    TextView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        toolbar_meeting=findViewById(R.id.toolbar_meeting);
        rv_genmeeting = findViewById(R.id.rv_genmeeting);
//      rv_spetialmeeting = findViewById(R.id.rv_spetialmeeting);
        btn_generalsave = findViewById(R.id.btn_generalsave);
//      btn_spetialsave = findViewById(R.id.btn_spetialsave);
        et_nogeneral = findViewById(R.id.et_nogeneral);
        //  et_nospetial = findViewById(R.id.et_nospetial);
        meetingtype=findViewById(R.id.meetingtype);
        back_arrow=findViewById(R.id.back_arrow);
        setSupportActionBar(toolbar_meeting);
        back_arrow.setText(toolbar_meeting.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = new ArrayList<>();
        pref= getApplicationContext().getSharedPreferences("MyPref",0);
        edit=pref.edit();
        btn_generalsave.setVisibility(View.GONE);
        // btn_spetialsave.setVisibility(View.GONE);

        et_nogeneral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                if(charSequence.toString().equals(""))
                {
                    btn_generalsave.setVisibility(View.GONE);
                    list.clear();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateMeeting.this, RecyclerView.VERTICAL, false);
                    rv_genmeeting.setLayoutManager(linearLayoutManager);
                    createMeetingAdaptor = new CreateMeetingAdaptor(CreateMeeting.this,list,CreateMeeting.this);
                    rv_genmeeting.setAdapter(createMeetingAdaptor);
                    Toast.makeText(CreateMeeting.this, "cannot be null", Toast.LENGTH_SHORT).show();

                }
                else {
                    if(et_nogeneral.getText().toString().matches("[0-9]+"))
                    {
                        int number = Integer.parseInt(et_nogeneral.getText().toString());
                        list.clear();
                        for (int j = 0; j < number; j++) {
                            CreateMeetingModel createMeetingModel = new CreateMeetingModel("", "");
                            list.add(createMeetingModel);
                        }
                        btn_generalsave.setVisibility(View.VISIBLE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateMeeting.this, RecyclerView.VERTICAL, false);
                        rv_genmeeting.setLayoutManager(linearLayoutManager);
                        createMeetingAdaptor = new CreateMeetingAdaptor(CreateMeeting.this, list, CreateMeeting.this);
                        rv_genmeeting.setAdapter(createMeetingAdaptor);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter valid number",Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        btn_generalsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int num1=0;
//                if(!et_nospetial.getText().toString().equals(""))
//                {
//                    num1=Integer.parseInt(et_nospetial.getText().toString());
//                }
                if(meetingtype.getSelectedItem().toString().equals("Select meeting type"))
                {
                    Toast.makeText(getApplicationContext(), "Please select Meeeting type", Toast.LENGTH_SHORT).show();
                    return;
                }
                int number=Integer.parseInt(et_nogeneral.getText().toString());
                if(meetingdate.size()==number&&meetingtitle.size()==number)
                {
                    String type=meetingtype.getSelectedItem().toString();
                    for (int i = 1; i <=number ; i++) {
                        if(meetingtitle.containsKey(i)&&meetingdate.containsKey(i))
                        {
                            setmeeting(i,type);
                        }

                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill all details",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void setmeeting(int i,String type) {
        Common common= new Common();
        String date=common.getFormatedDate(meetingdate.get(i));
        Date date1=new Date();
        String newdate= String.format(date, date1);

        Retrofit.Builder builder= new Retrofit.Builder()
                .baseUrl(Common.Companion.getBASE_URL()).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiService apiService = retrofit.create(ApiService.class);
        final int finalI = i;
        apiService.addmeeting(pref.getString("S_id",""),meetingtitle.get(i),type,date)
                .enqueue(new Callback<List<ServerResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                        if(response.body().get(0).getServerResponse().equals(1)&&response.isSuccessful())
                        {
                            et_nogeneral.setText("");
                            Toast.makeText(getApplicationContext(),"Meeting has been scheduled for date "+meetingdate.get(finalI),Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed to schedule meeting for date "+meetingdate.get(finalI),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void title(Integer pos, String title) {
        if(!title.equals(""))
        {
            meetingtitle.put(pos+1,title);
        }
    }

    @Override
    public void date(Integer pos, String date) {
        if(!date.equals(""))
        {
            meetingdate.put(pos+1,date);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
