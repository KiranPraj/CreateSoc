package com.icspl.createsoc.Activitys;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText society_name, admin_name, email, mobile;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        society_name = findViewById(R.id.et_soc_name);
        admin_name = findViewById(R.id.et_admin);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mob);
        create = findViewById(R.id.btn_create);

        create.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateuser();
            }
        });

    }

    private void validateuser() {
        String Society_name = society_name.getText().toString();
        String Admin_name = admin_name.getText().toString();
        String Email = email.getText().toString();
        String Mobile = mobile.getText().toString();
        final String emailpattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (Society_name.isEmpty()) {
            society_name.setError("enter society Name");
        } else if (Admin_name.isEmpty()) {
            admin_name.setError("enter admin name");
        } else if (Email.isEmpty()) {
            email.setError("enter email");
        } else if (!Email.matches(emailpattern)) {
            email.setError("enter proper Mail");
        } else if (Mobile.isEmpty()) {
            mobile.setError("enter Mobile Number");
        } else if (Mobile.length() != 10) {
            mobile.setError("enter proper number");
        } else {
            society_name.setText("");
            admin_name.setText("");
            email.setText("");
            mobile.setText("");
            CreateSociety(Society_name, Admin_name, Email, Mobile);


        }
    }

    private void CreateSociety(String society_name, String admin_name, String email, String mobile) {
        String base = Common.Companion.getBASE_URL();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiService= retrofit.create(ApiService.class);
        apiService.register(society_name,admin_name,email,mobile)
                .enqueue(new Callback<List<ServerResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                        if(response.isSuccessful())
                        {
                            int responses=response.body().get(0).getServerResponse();
                            if(responses==1)
                            {
                                Toast.makeText(MainActivity.this,"Created",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Error in server response",Toast.LENGTH_LONG).show();
                            }

                        }
                        else {

                        }

                    }

                    @Override
                    public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"Something wrong",Toast.LENGTH_LONG).show();
                    }
                });
    }
}

