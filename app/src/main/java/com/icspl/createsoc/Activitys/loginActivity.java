
package com.icspl.createsoc.Activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.icspl.createsoc.callback.ApiService;
import com.icspl.createsoc.Model.MemberDetails;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginActivity extends AppCompatActivity {
    TextInputEditText username, password;
    ImageView login;
    Spinner user;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);   // //58485// krishna.gajula@icsasian.com
        login = findViewById(R.id.login);
        user = findViewById(R.id.user);
        pref= getApplicationContext().getSharedPreferences("MyPref", 0);
        edit=pref.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateuser();
            }
        });
    }

    private void validateuser() {
        // loadingProgressBar.visibility=View.VISIBLE
        String Username = username.getText().toString();
        String Password = password.getText().toString();
         User = user.getSelectedItem().toString();


        if (Username.isEmpty()) {
            username.setError("Enter Uername");
        } else if (Password.isEmpty()) {
            password.setError("Enter Password");
        } else {
            if (!User.equals("Select user")) {
                if (User.equals("Master Admin")) {
                    if ((Username.equals("ICST/1")) && Password.equals("***")) {
                        permissionaccess();

                    } else {
                        Toast.makeText(loginActivity.this, "Enter correct id or password", Toast.LENGTH_LONG).show();
                    }

                } else if (User.equals("Admin")) {
                    fetchLogin(Username, Password, User);

                } else {

                    Intent intent = new Intent(loginActivity.this, Homescreen.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(loginActivity.this, "select user", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchLogin(String username, String password, String type) {

       Common common= new Common();
        common.getAPI().checklogin(username,password,type).enqueue(new Callback<List<MemberDetails>>() {
            @Override
            public void onResponse(Call<List<MemberDetails>> call, Response<List<MemberDetails>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    Toast.makeText(loginActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                    String sid = response.body().get(0).getSocietyId();
                    String society=response.body().get(0).getSocietyName();
                    edit.putString("Societyname",society);
                    edit.putString("S_id",sid);
                    edit.apply();
                   permissionaccess();
                } else {
                    Toast.makeText(loginActivity.this, "Enter correct id/password or society not registered ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MemberDetails>> call, Throwable t) {
                Toast.makeText(loginActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void permissionaccess()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Dexter.withActivity(loginActivity.this)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)



              .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                       if (User.equals("Master Admin"))
                       {
                           Intent intent = new Intent(loginActivity.this, MainActivity.class);
                           startActivity(intent);
                       }
                       else
                       {
                           Intent intent = new Intent(loginActivity.this, Homescreen.class);
                           startActivity(intent);
                       }

                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        // show alert dialog navigating to Settings
                        showsetting();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).
                    withErrorListener(new PermissionRequestErrorListener() {
                        @Override
                        public void onError(DexterError error) {
                            Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onSameThread()
                    .check();
        }

    }

    private void showsetting()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings()
    {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }



}