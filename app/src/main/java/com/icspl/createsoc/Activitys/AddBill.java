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

public class AddBill extends AppCompatActivity {
    Toolbar toolbar;
    TextView back_textview;
    EditText expense_desc,account_ledger,bill_rate;
    Spinner account_grp,expense_type,billing_typ;
    Button save_newbill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        expense_desc=findViewById(R.id.edit_expence_description);
        account_ledger=findViewById(R.id.edit_account_ledger);
        bill_rate=findViewById(R.id.edit_billrate);
        account_grp=findViewById(R.id.account_grp_spinner);
        expense_type=findViewById(R.id.expense_spiner);
        billing_typ=findViewById(R.id.billing_spiner);
        save_newbill=findViewById(R.id.btn_save_bill);
        toolbar=findViewById(R.id.toolbar_addbill);
        back_textview=findViewById(R.id.back_arrow_addbill);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        save_newbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if ((expense_desc.getText().toString()).equals("")||((account_ledger.getText().toString()).equals(""))|| ((account_grp.getSelectedItem().toString()).equals("Select")) ||((expense_type.getSelectedItem().toString()).equals("Select"))
                || ((billing_typ.getSelectedItem().toString()).equals("Select")) || ((bill_rate.getText().toString()).equals("")))
                {
                    Toast.makeText(AddBill.this, "fill expense description", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if ((expense_desc.getText().toString()).equals(""))
                {
                    Toast.makeText(AddBill.this, "fill expense description", Toast.LENGTH_SHORT).show();
                    return;
                }
                    if ((account_ledger.getText().toString()).equals("")) {
                        Toast.makeText(AddBill.this, "Fill Account Ledger", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((account_grp.getSelectedItem().toString()).equals("Select")) {
                        Toast.makeText(AddBill.this, "Select Account Group", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((expense_type.getSelectedItem().toString()).equals("Select")) {
                        Toast.makeText(AddBill.this, "Select Expense Type", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((billing_typ.getSelectedItem().toString()).equals("Select")) {
                        Toast.makeText(AddBill.this, "Select Billing Type", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((bill_rate.getText().toString()).equals("")) {
                        Toast.makeText(AddBill.this, "Fill bill rate", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        Toast.makeText(AddBill.this, "All fields are filled..", Toast.LENGTH_LONG).show();
                        return;

                    }
                }

        });
    }
}
