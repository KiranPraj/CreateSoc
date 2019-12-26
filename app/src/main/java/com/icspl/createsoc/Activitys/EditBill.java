package com.icspl.createsoc.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.R;

public class EditBill extends AppCompatActivity {
    Toolbar toolbar;
    TextView back_textview;
    EditText expense_desc,account_ledger,bill_rate;
    Spinner account_grp,expense_type,billing_typ,sr_no;
    Button save_editbillbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill);
        expense_desc=findViewById(R.id.edit_expence_description);
        account_ledger=findViewById(R.id.edit_account_ledger);
        bill_rate=findViewById(R.id.edit_billrate);
        account_grp=findViewById(R.id.account_grp_spinner);
        expense_type=findViewById(R.id.expense_spiner);
        billing_typ=findViewById(R.id.billing_spiner);
        sr_no=findViewById(R.id.spineer_srno);
        save_editbillbtn=findViewById(R.id.btn_saveedit_bill);
        toolbar=findViewById(R.id.toolbar_editbill);
        back_textview=findViewById(R.id.back_arrow_editbill);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        save_editbillbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if ((expense_desc.getText().toString()).equals("")||((account_ledger.getText().toString()).equals(""))|| ((account_grp.getSelectedItem().toString()).equals("Select")) ||((expense_type.getSelectedItem().toString()).equals("Select"))
                || ((billing_typ.getSelectedItem().toString()).equals("Select")) || ((bill_rate.getText().toString()).equals("")))
                {
                    Toast.makeText(EditBill.this, "fill expense description", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if ((sr_no.getSelectedItem().toString()).equals("Select"))
                {
                    Toast.makeText(EditBill.this, "select Sr no", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((expense_desc.getText().toString()).equals(""))
                {
                    Toast.makeText(EditBill.this, "fill expense description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((account_ledger.getText().toString()).equals("")) {
                    Toast.makeText(EditBill.this, "Fill Account Ledger", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((account_grp.getSelectedItem().toString()).equals("Select")) {
                    Toast.makeText(EditBill.this, "Select Account Group", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((expense_type.getSelectedItem().toString()).equals("Select")) {
                    Toast.makeText(EditBill.this, "Select Expense Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((billing_typ.getSelectedItem().toString()).equals("Select")) {
                    Toast.makeText(EditBill.this, "Select Billing Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((bill_rate.getText().toString()).equals("")) {
                    Toast.makeText(EditBill.this, "Fill bill rate", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Toast.makeText(EditBill.this, "All fields are filled..", Toast.LENGTH_LONG).show();
                    return;

                }
            }

        });
    }
}
