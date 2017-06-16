package br.edu.leaosampaio.CityCare.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.edu.leaosampaio.CityCare.Fragments.MapsFragment;
import br.edu.leaosampaio.CityCare.Fragments.PostagensFragment;
import br.edu.leaosampaio.CityCare.Fragments.ProfileFragment;

/**
 * Created by lenilson on 15/06/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public TabAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PostagensFragment tab1 = new PostagensFragment();
                return tab1;
            case 1:
                MapsFragment tab2 = new MapsFragment();
                return tab2;
            case 2:
                ProfileFragment tab3 = new ProfileFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
