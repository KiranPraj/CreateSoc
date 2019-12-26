package com.icspl.createsoc.Activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.icspl.createsoc.Fragments.Co_owner_Fragment;
import com.icspl.createsoc.Fragments.FlatOwnerDetailsFragment;
import com.icspl.createsoc.Fragments.Owner_documentFragment;
import com.icspl.createsoc.R;

public class Society_FlatActivity extends AppCompatActivity
{

TextView back_arrow;
FrameLayout frameLayout;
Toolbar toolbar;
String Flat_id;
MenuItem menuItemOwner,menuItemCo_owner,menuItemDocuments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__flat);
        back_arrow=findViewById(R.id.back_arrow);
        frameLayout=findViewById(R.id.framelayout);
        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        back_arrow.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent i=getIntent();
        Flat_id=i.getStringExtra("Flatid");
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
          fragmentTransaction.replace(R.id.framelayout,new FlatOwnerDetailsFragment()).commit();

    }

    public Bundle getData()
    {
        Bundle bundle=new Bundle();
        bundle.putString("MainFlatId", Flat_id);
        return bundle ;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.flat_menu,menu);
   menuItemOwner=menu.findItem(R.id.action_Owner_details);
    menuItemCo_owner=menu.findItem(R.id.action_owner_moredetails);
     menuItemDocuments=menu.findItem(R.id.action_coOwner_moredetails);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        menuItemOwner.setVisible(false);
        if(id== R.id.action_Owner_details)
        {
            menuItemOwner.setVisible(false);
            menuItemCo_owner.setVisible(true);
            menuItemDocuments.setVisible(true);
            FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,new FlatOwnerDetailsFragment()).commit();

            Toast.makeText(this, "owner here", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id== R.id.action_owner_moredetails)
        {
            menuItemOwner.setVisible(true);
            menuItemCo_owner.setVisible(false);
            menuItemDocuments.setVisible(true);
            FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,new Co_owner_Fragment()).commit();

            Toast.makeText(this, "co_owner here", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id== R.id.action_coOwner_moredetails)
        {
            menuItemOwner.setVisible(true);
            menuItemCo_owner.setVisible(true);
            menuItemDocuments.setVisible(false);
            FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,new Owner_documentFragment()).commit();

            Toast.makeText(this, "document  here", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
