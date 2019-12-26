package com.icspl.createsoc.Adaptor;

import com.icspl.createsoc.VIewpagerEditsoc.BlockVP;
import com.icspl.createsoc.VIewpagerEditsoc.FlatVP;
import com.icspl.createsoc.VIewpagerEditsoc.OwnerOfFlatVP;
import com.icspl.createsoc.VIewpagerEditsoc.SocietyDetailsVP;
import com.icspl.createsoc.VIewpagerEditsoc.WingVP;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AllpagerAdaptor extends FragmentStatePagerAdapter {

    int mNooftabs;

    public AllpagerAdaptor(FragmentManager fm, int mNooftabs) {
        super(fm);
        this.mNooftabs = mNooftabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SocietyDetailsVP societyDetailsVP = new SocietyDetailsVP();
                return societyDetailsVP;
            case 1:
                BlockVP blockVP = new BlockVP();
                return blockVP;
            case 2:
                WingVP wingVP = new WingVP();
                return wingVP;
            case 3:
                FlatVP flatVP = new FlatVP();
                return flatVP;
            case 4:
                OwnerOfFlatVP ownerOfFlatVP = new OwnerOfFlatVP();
                return ownerOfFlatVP;
            default:

                return null;
        }


    }

    @Override
    public int getCount() {
        return mNooftabs;
    }
}
