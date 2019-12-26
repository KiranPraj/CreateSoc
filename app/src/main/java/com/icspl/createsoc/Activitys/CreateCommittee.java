package com.icspl.createsoc.Activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.CreateCommitteeAdaptor;
import com.icspl.createsoc.Model.CreateCommitteeModel;
import com.icspl.createsoc.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateCommittee extends AppCompatActivity {
    public RadioGroup radiogroup;
    public RadioButton radio_Yes;
    public RadioButton radio_No;
    public Spinner spin_CBlock;
    public Spinner spin_CWings;
    public LinearLayout noofdesignation;
    public EditText et_enterdesignationno;
    public RecyclerView rv_designation;
    public Button btn_Savedesignation;
    public List<CreateCommitteeModel> list;
    public CreateCommitteeAdaptor createCommitteeAdaptor;
    private LinearLayout blocks,wings;
    Toolbar toolbar;
    TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_committee);
        toolbar=findViewById(R.id.toolbar_create_comite);
        back_textview=findViewById(R.id.back_arrow_create_comite);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        radiogroup = findViewById(R.id.radiogroup);
        radio_Yes = findViewById(R.id.radio_Yes);
        radio_No = findViewById(R.id.radio_No);
        spin_CBlock = findViewById(R.id.spin_CBlock);
        spin_CWings = findViewById(R.id.spin_CWings);
        noofdesignation = findViewById(R.id.noofdesignation);
        et_enterdesignationno = findViewById(R.id.et_enterdesignationno);
        rv_designation = findViewById(R.id.rv_designation);
        btn_Savedesignation = findViewById(R.id.btn_Savedesignation);
        blocks=findViewById(R.id.blocks);
        wings=findViewById(R.id.wings);

        list = new ArrayList<>();

//        spin_CBlock.setVisibility(View.GONE);
//        spin_CWings.setVisibility(View.GONE);
        wings.setVisibility(View.GONE);
        blocks.setVisibility(View.GONE);
        noofdesignation.setVisibility(View.GONE);
        btn_Savedesignation.setVisibility(View.GONE);


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_No:
                if (checked)
//                    spin_CBlock.setVisibility(View.VISIBLE);
//                spin_CWings.setVisibility(View.VISIBLE);
                    blocks.setVisibility(View.VISIBLE);
                wings.setVisibility(View.VISIBLE);
                noofdesignation.setVisibility(View.VISIBLE);
                btn_Savedesignation.setVisibility(View.VISIBLE);
                et_enterdesignationno.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        list.clear();

                        if(editable.toString().equals(""))
                        {
                            list.clear();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateCommittee.this, RecyclerView.VERTICAL, false);
                            rv_designation.setLayoutManager(linearLayoutManager);
                            createCommitteeAdaptor = new CreateCommitteeAdaptor(CreateCommittee.this,list);
                            rv_designation.setAdapter(createCommitteeAdaptor);
                            Toast.makeText(CreateCommittee.this, "cannot be null", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            int number=Integer.parseInt(et_enterdesignationno.getText().toString());
                            list.clear();

                            for (int j = 0; j < number; j++) {
                                CreateCommitteeModel createMeetingModel = new CreateCommitteeModel("");
                                list.add(createMeetingModel);


                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateCommittee.this, RecyclerView.VERTICAL, false);
                            rv_designation.setLayoutManager(linearLayoutManager);
                            createCommitteeAdaptor = new CreateCommitteeAdaptor(CreateCommittee.this,list);
                            rv_designation.setAdapter(createCommitteeAdaptor);


                        }
                    }
                });
                    // Pirates are the best
                    break;
            case R.id.radio_Yes:
                if (checked)
//                    spin_CBlock.setVisibility(View.GONE);
//                spin_CWings.setVisibility(View.GONE);
                    blocks.setVisibility(View.GONE);
                wings.setVisibility(View.GONE);
                noofdesignation.setVisibility(View.VISIBLE);
                btn_Savedesignation.setVisibility(View.VISIBLE);
                // Ninjas rule

                et_enterdesignationno.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        list.clear();

                        if(editable.toString().equals(""))
                        {
                            list.clear();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateCommittee.this, RecyclerView.VERTICAL, false);
                            rv_designation.setLayoutManager(linearLayoutManager);
                            createCommitteeAdaptor = new CreateCommitteeAdaptor(CreateCommittee.this,list);
                            rv_designation.setAdapter(createCommitteeAdaptor);
                            Toast.makeText(CreateCommittee.this, "cannot be null", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            int number=Integer.parseInt(et_enterdesignationno.getText().toString());
                            list.clear();

                            for (int j = 0; j < number; j++) {
                                CreateCommitteeModel createMeetingModel = new CreateCommitteeModel("");
                                list.add(createMeetingModel);


                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateCommittee.this, RecyclerView.VERTICAL, false);
                            rv_designation.setLayoutManager(linearLayoutManager);
                            createCommitteeAdaptor = new CreateCommitteeAdaptor(CreateCommittee.this,list);
                            rv_designation.setAdapter(createCommitteeAdaptor);


                        }
                    }
                });
                    break;
        }
    }
}


