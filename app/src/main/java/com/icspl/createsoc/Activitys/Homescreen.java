package com.icspl.createsoc.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.AdaptorHOme;
import com.icspl.createsoc.DbHelper.DbHelper;
import com.icspl.createsoc.Model.Homescreenmodel;
import com.icspl.createsoc.R;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Homescreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public List<Homescreenmodel> list;
    public RecyclerView rv;
    public Context mContext;
    SharedPreferences pref;
    DbHelper dbHelper;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescree);
        pref= getApplicationContext().getSharedPreferences("MyPref", 0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mContext=Homescreen.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(pref.getString("Societyname",""));
        rv=findViewById(R.id.rv);
        dbHelper = new DbHelper(Homescreen.this);
        mDatabase = dbHelper.getWritableDatabase();
        Homescreenmodel homescreenmodel = new Homescreenmodel(R.drawable.editso,"SOCIETY DETAILS");
        Homescreenmodel homescreenmodel1= new Homescreenmodel(R.drawable.meeting,"SOCIETY MEETINGS");
      Homescreenmodel homescreenmodel2 = new Homescreenmodel(R.drawable.notice,"NOTICE");
      Homescreenmodel  homescreenmodel3 = new Homescreenmodel(R.drawable.election,"ELECTION");
       Homescreenmodel  homescreenmodel4 = new Homescreenmodel(R.drawable.hh,"COMMITTEE");
       Homescreenmodel homescreenmodel5 = new Homescreenmodel(R.drawable.acc,"ACCOUNT MANAGE");
Homescreenmodel homescreenmodel6 = new Homescreenmodel(R.drawable.complaint,"COMPLAINTS");
Homescreenmodel homescreenmodel7 = new Homescreenmodel(R.drawable.profile,"PROFILE");
        list= new ArrayList<>();
list.add(homescreenmodel);
list.add(homescreenmodel1);
list.add(homescreenmodel2);
list.add(homescreenmodel3);
list.add(homescreenmodel4);
list.add(homescreenmodel5);
list.add(homescreenmodel6);
list.add(homescreenmodel7);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext,2);
        rv.setLayoutManager(layoutManager);
     AdaptorHOme adaptorHOme = new AdaptorHOme(Homescreen.this,list);
        rv.setAdapter(adaptorHOme);


              ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homescree, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile_menu)
        {
            // Handle the camera action
        } else if (id == R.id.society_details_menu)
        {
            Intent i=new Intent(mContext,Society_Details.class);
            startActivity(i);

        } else if (id == R.id.society_meeting_menu) {
            Intent i=new Intent(mContext,Meetings.class);
            startActivity(i);

        } else if (id == R.id.notice_menu) {
            Intent i=new Intent(mContext,Notice.class);
            startActivity(i);

        } else if (id == R.id.election_menu) {
           // Intent i=new Intent(mContext,Society_Details.class);
           // startActivity(i);

        }
        else if (id == R.id.committee_menu) {
            Intent i=new Intent(mContext,Committee.class);
            startActivity(i);

        }
        else if (id == R.id.account_mange_menu) {
            Intent i=new Intent(mContext,AccountManage.class);
            startActivity(i);

        }
        else if (id == R.id.complaints_menu) {
            Intent i=new Intent(mContext,Complaints.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
