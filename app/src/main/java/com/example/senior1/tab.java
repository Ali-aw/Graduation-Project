package com.example.senior1;

//mainactvty


import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class tab extends AppCompatActivity {

    // Initialize variables
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        // assign variable
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);

        // Initialize array list
        ArrayList<String> arrayList=new ArrayList<>(0);

        // Add title in array list
        arrayList.add("Login-Register");
        arrayList.add("List");
        arrayList.add("Profile");

        // Setup tab layout
        tabLayout.setupWithViewPager(viewPager);

        // Prepare view pager
        prepareViewPager(viewPager,arrayList);

    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        // Initialize main adapter
        MainAdapter adapter=new MainAdapter(getSupportFragmentManager());

        // Initialize main fragment
        MainFragment mainFragment=new MainFragment();


        // Use for loop


            Bundle bundle=new Bundle();

            bundle.putString("title",arrayList.get(0));
            mainFragment.setArguments(bundle);
            adapter.addFragment(mainFragment,arrayList.get(0));


        fragmentList fragmentList=new fragmentList();
        bundle.putString("title",arrayList.get(1));
        fragmentList.setArguments(bundle);
        adapter.addFragment(fragmentList,arrayList.get(1));


       profileFragment  p=new profileFragment();


        bundle.putString("title",arrayList.get(2));
        p.setArguments(bundle);
        adapter.addFragment(p,arrayList.get(2));



        // set adapter
        viewPager.setAdapter(adapter);

    }

    private class MainAdapter extends FragmentPagerAdapter {
        // Initialize arrayList
        ArrayList<Fragment> fragmentArrayList= new ArrayList<>();
        ArrayList<String> stringArrayList=new ArrayList<>();

        //int[] imageList={R.drawable.basic,R.drawable.advance,R.drawable.pro};

        // Create constructor
        public void addFragment(Fragment fragment,String s)
        {
            // Add fragment
            fragmentArrayList.add(fragment);
            // Add title
            stringArrayList.add(s);
        }

        public MainAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // return fragment position
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            // Return fragment array list size
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            // Initialize drawable
            // Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),imageList[position]);

            // set bound
            // drawable.setBounds(0,0,drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            // Initialize spannable image
            SpannableString spannableString=new SpannableString(""+stringArrayList.get(position));

            // Initialize image span
            //ImageSpan imageSpan=new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);

            // Set span
            spannableString.setSpan(null,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // return spannable string
            return spannableString;
        }
    }
}