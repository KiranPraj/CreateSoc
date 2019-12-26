package com.icspl.createsoc.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icspl.createsoc.R;

public class Society_Details extends AppCompatActivity {
    private Button btn_editsoc,btn_view;
    Toolbar toolbar;
    TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__details);
        toolbar=findViewById(R.id.toolbar_s);
        back_textview=findViewById(R.id.back_arrow_s);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_editsoc=findViewById(R.id.btn_editsoc);
        btn_view=findViewById(R.id.btn_view);
        btn_editsoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Society_Details.this,EditSocActivity.class);
                startActivity(intent);
            }
        });


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Society_Details.this,ViewsocActivity.class);
                startActivity(intent);
            }
        });
    }
}
