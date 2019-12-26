package com.icspl.createsoc.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.icspl.createsoc.Adaptor.AllpagerAdaptor;
import com.icspl.createsoc.R;
import com.icspl.createsoc.VIewpagerEditsoc.BlockVP;
import com.icspl.createsoc.VIewpagerEditsoc.FlatVP;
import com.icspl.createsoc.VIewpagerEditsoc.OwnerOfFlatVP;
import com.icspl.createsoc.VIewpagerEditsoc.SocietyDetailsVP;
import com.icspl.createsoc.VIewpagerEditsoc.WingVP;
import com.google.android.material.tabs.TabLayout;

public class EditSocActivity extends AppCompatActivity implements SocietyDetailsVP.OnFragmentInteractionListener, BlockVP.OnFragmentInteractionListener, WingVP.OnFragmentInteractionListener, FlatVP.OnFragmentInteractionListener, OwnerOfFlatVP.OnFragmentInteractionListener {
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Toolbar toolbar;
    TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_soc);
        pref= getApplicationContext().getSharedPreferences("MyPref", 0);
        edit=pref.edit();
        toolbar=findViewById(R.id.toolbar_es);
        back_textview=findViewById(R.id.back_arrow_es);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout .newTab().setText("SOCIETY"));
        tabLayout.addTab(tabLayout .newTab().setText("BLOCK"));
        tabLayout.addTab(tabLayout .newTab().setText("WING"));
        tabLayout.addTab(tabLayout .newTab().setText("FLAT"));
        tabLayout.addTab(tabLayout .newTab().setText("OWNER"));
tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

final ViewPager viewPager = findViewById(R.id.pager);
final AllpagerAdaptor allpagerAdaptor = new AllpagerAdaptor(getSupportFragmentManager(),tabLayout.getTabCount());
viewPager.setAdapter(allpagerAdaptor);
viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        edit.remove("flatid");
        edit.apply();
    }
}
