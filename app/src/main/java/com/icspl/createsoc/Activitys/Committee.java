package com.icspl.createsoc.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icspl.createsoc.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Committee extends AppCompatActivity {
public Button btn_createcommitee,btn_viewcommittee;
    Toolbar toolbar;
    TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);
        toolbar=findViewById(R.id.toolbar_acomitte);
        back_textview=findViewById(R.id.back_arrow_acomitte);
        setSupportActionBar(toolbar);
     //   getSupportActionBar().setTitle("Committee");
       back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_createcommitee=findViewById(R.id.btn_createcommitee);

        btn_createcommitee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Committee.this, CreateCommittee.class);
                startActivity(intent);
            }
        });

    }
}
