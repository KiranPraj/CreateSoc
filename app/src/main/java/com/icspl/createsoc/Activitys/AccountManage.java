package com.icspl.createsoc.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icspl.createsoc.R;

public class AccountManage extends AppCompatActivity {
public Button btn_oldbill,btn_newBill;
    Toolbar toolbar;
    TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        btn_newBill=findViewById(R.id.btn_newBill);
        btn_oldbill=findViewById(R.id.btn_oldbill);
        toolbar=findViewById(R.id.toolbar_a);
        back_textview=findViewById(R.id.back_arrow_a);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_oldbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountManage.this,EditBill.class);
                startActivity(intent);
            }
        });
        btn_newBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountManage.this,AddBill.class);
                startActivity(intent);
            }
        });

    }
}
