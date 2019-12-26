package com.icspl.createsoc.Activitys;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Adaptor.CreateMeetingAdaptor;
import com.icspl.createsoc.Adaptor.ViewMeetingAdapter;
import com.icspl.createsoc.Model.ViewMeetingModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMeeting extends AppCompatActivity {
    AppCompatSpinner type;
    AppCompatButton submit;
    TextView meetingdate;
    RecyclerView rv_viewmeeting;
    List<ViewMeetingModel> meetinglist;
    TextView back_arrow;
    Toolbar toolbar;
    final Calendar mycalender =  Calendar.getInstance();
    ViewMeetingAdapter viewMeetingAdapter;
    SharedPreferences preferences;
    String s_id;
    String ddate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);
        meetingdate= findViewById(R.id.tv_viewmeetingdate);
        type=findViewById(R.id.sp_type);
        submit= findViewById(R.id.btn_submit);
        rv_viewmeeting= findViewById(R.id.rv_ViewMeeting);
        back_arrow=findViewById(R.id.back_arrow);
        meetinglist=new ArrayList<>();
        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        back_arrow.setText(toolbar.getTitle());
        preferences=getApplicationContext().getSharedPreferences("MyPref", 0);
        s_id=preferences.getString("S_id","");
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                datef();
            }
        } ;
        meetingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ViewMeeting.this,dateSetListener,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_viewmeeting.setLayoutManager(layoutManager);
        viewMeetingAdapter = new ViewMeetingAdapter(ViewMeeting.this,meetinglist);
        rv_viewmeeting.setAdapter(viewMeetingAdapter);

        function();
    }
    private void datef() {
        String Myformat ="MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Myformat);
        meetingdate.setText(simpleDateFormat.format(mycalender.getTime()));
     ddate=simpleDateFormat.format(mycalender.getTime());


    }
    private void function() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetinglist.clear();
                if(meetingdate.getText().toString().equals(""))
                {
                    Toast.makeText(ViewMeeting.this, "Please select date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(type.getSelectedItem().toString().equals("Select meeting type"))
                {
                    Toast.makeText(ViewMeeting.this, "Please select Meeting type", Toast.LENGTH_SHORT).show();
                    return;
                }
                Common common= new Common();
                common.getAPI().getmeetingdetail(s_id,type.getSelectedItem().toString(),ddate)
                        .enqueue(new Callback<List<ViewMeetingModel>>() {
                            @Override
                            public void onResponse(Call<List<ViewMeetingModel>> call, Response<List<ViewMeetingModel>> response) {
                                if(response.isSuccessful()&& response.body().size()>0)
                                {
                                  /* ArrayList viewMeetingModel=new ArrayList();
                                    meetinglist.clear();
                                    for (int i = 0; i <response.body().size() ; i++) {

                                       viewMeetingModel.add(meetinglist);
                                    }*/
                                   /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewMeeting.this, RecyclerView.VERTICAL, false);
                                    rv_viewmeeting.setLayoutManager(linearLayoutManager);
                                    viewMeetingAdapter = new ViewMeetingAdapter(ViewMeeting.this,meetinglist);
                                   // rv_viewmeeting.setAdapter(viewMeetingAdapter);
                                    rv_viewmeeting.setAdapter(viewMeetingAdapter);*/

                                    //meetinglist=response.body()


                                    meetinglist=response.body();
                                    viewMeetingAdapter.setArray(meetinglist);

                                }
                                else
                                {
                                    viewMeetingAdapter.notifyDataSetChanged();
                                    meetinglist.clear();
                                    Toast.makeText(ViewMeeting.this, "No Meeting Schdule For this date..", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ViewMeetingModel>> call, Throwable t) {

                            }
                        });
            }
        });
    }
}
