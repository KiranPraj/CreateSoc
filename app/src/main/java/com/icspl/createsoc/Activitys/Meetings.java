package com.icspl.createsoc.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icspl.createsoc.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Meetings extends AppCompatActivity {
public Button btn_createmeeting,btn_viewmeeting;
Toolbar toolbar;
TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        btn_createmeeting=findViewById(R.id.btn_createmeeting);
        btn_viewmeeting=findViewById(R.id.btn_viewmeeting);
        toolbar=findViewById(R.id.toolbar_m);
        back_textview=findViewById(R.id.back_arrow_m);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_createmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Createmeeting();
            }
        });
        btn_viewmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(Meetings.this,ViewMeeting.class);
                startActivity(intent);
            }
        });

    }

    private void Createmeeting() {
        Intent intent = new Intent(Meetings.this,CreateMeeting.class);
        startActivity(intent);

    }
}
