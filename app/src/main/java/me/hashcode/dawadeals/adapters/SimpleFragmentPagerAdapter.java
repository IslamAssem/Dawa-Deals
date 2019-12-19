package me.hashcode.dawadeals.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
//import com.android.internal.

/**
 * Created by Islam on 10-Oct-17.
 */

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    public void add(Fragment fragment, String title) {
        add(fragment,title,false);
    }

    public void remove(Fragment fragment){
        if (getCount()==0)
            return;
        int index = fragments.indexOf(fragment);
        if (index == -1)
            return;
        else remove(index);
    }

    private void remove(int index) {
        if (index<0||index>=getCount())
            return;
        fragments.remove(index);
        titles.remove(index);
        notifyDataSetChanged();
    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    public void add(Fragment fragment, String title,boolean notify) {
        if(fragment==null)
            return;
        if (fragments == null || titles == null) {
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }
        if (fragments.contains(fragment))
            return;
        fragments.add(fragment);
        titles.add(title);
        if(notify)
            notifyDataSetChanged();
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

//        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
         if (fragments==null)
            return 0;
        return fragments.size();
    }

    // This determines the title for each tab
    @Override
    public String getPageTitle(int position) {
        return titles.get(position);
    }

}



