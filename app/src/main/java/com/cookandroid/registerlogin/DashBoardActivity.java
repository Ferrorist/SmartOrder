package com.cookandroid.registerlogin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager2 viewpager;
    private TabLayout tabLayout;
    private String userID;
    private String[] titles = {"정보센터식당", "복지관 교직원식당", "카페테리아 첨성", "GP감꽃푸드코트", "공학식당1", "공학식당2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        initView();
    }

    private void initView() {
        viewpager = findViewById(R.id.ViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        fragmentList.add(RestFragment.newInstance("6", "")); // dummy1
        fragmentList.add(RestFragment.newInstance("1", userID));
        fragmentList.add(RestFragment.newInstance("2", userID));
        fragmentList.add(RestFragment.newInstance("3", userID));
        fragmentList.add(RestFragment.newInstance("4", userID));
        fragmentList.add(RestFragment.newInstance("5", userID));
        fragmentList.add(RestFragment.newInstance("6", userID));
        fragmentList.add(RestFragment.newInstance("1", "")); // dummy2

        viewpager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) { // 페이지가 선택될때마다 호출됨. position: 선택된 페이지의 index
                super.onPageSelected(position);
                if(position == 0) {
                    viewpager.setCurrentItem(fragmentList.size()-2, false);
                    tabLayout.selectTab(tabLayout.getTabAt(tabLayout.getTabCount()-1));
                }
                else if(position == fragmentList.size() - 1)    {
                    viewpager.setCurrentItem(1,false);
                    tabLayout.selectTab(tabLayout.getTabAt(0));
                }
                else {
                    tabLayout.selectTab(tabLayout.getTabAt(position-1));
                }
            }
        });

        for(int i = 0; i < 6; i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                viewpager.setCurrentItem(pos+1,false);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.setCurrentItem(1,false);
    }
}
