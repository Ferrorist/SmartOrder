package com.cookandroid.registerlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class testActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager2 viewpager;
    private TabLayout tabLayout;
    private String userID;
    private String[] titles = {"정보센터식당", "복지관 교직원식당", "카페테리아 첨성", "GP감꽃푸드코트", "공학식당1", "공학식당2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        initView();
    }

    private void initView() {
        viewpager = findViewById(R.id.ViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        fragmentList.add(RestFragment.newInstance("1", userID));
        fragmentList.add(RestFragment.newInstance("2", userID));
        fragmentList.add(RestFragment.newInstance("3", userID));
        fragmentList.add(RestFragment.newInstance("4", userID));
        fragmentList.add(RestFragment.newInstance("5", userID));
        fragmentList.add(RestFragment.newInstance("6", userID));

        viewpager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
//                Log.i("log_i","createFragment position: " + position);
                return fragmentList.get(position%6);
            }

            @Override
            public int getItemCount() {
                return 300;
//                return fragmentList.size();
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position%6]);
//                Log.i("log_i","onConfigureTab position: " + position);
            }
        });
        tabLayoutMediator.attach();
    }

}