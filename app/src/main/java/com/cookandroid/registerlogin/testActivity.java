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
    private String[] titles = {"test1","정보센터식당", "복지관 교직원식당", "카페테리아 첨성", "GP감꽃푸드코트", "공학식당1", "공학식당2","test2"};

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

        fragmentList.add(RestFragment.newInstance("6", userID)); // test1
        fragmentList.add(RestFragment.newInstance("1", userID));
        fragmentList.add(RestFragment.newInstance("2", userID));
        fragmentList.add(RestFragment.newInstance("3", userID));
        fragmentList.add(RestFragment.newInstance("4", userID));
        fragmentList.add(RestFragment.newInstance("5", userID));
        fragmentList.add(RestFragment.newInstance("6", userID));
        fragmentList.add(RestFragment.newInstance("1", userID)); // test2


        viewpager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
//                Log.i("log_i","createFragment position: " + position);
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
//                return 300;
                return fragmentList.size();
            }
        });
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) { // 페이지가 선택될때마다 호출됨. position: 선택된 페이지의 index
                super.onPageSelected(position);
                Log.i("log_i","onPageSelected position: " + position);
                if(position == 0) {
                    viewpager.setCurrentItem(fragmentList.size()-2, false);
                    Log.i("log_i","move page from start to end");
                    Log.i("log_i","onPageSelected position: " + position);
                }
                else if(position == fragmentList.size() - 1)    {
                    viewpager.setCurrentItem(1,false);
                    Log.i("log_i","move page from end to start");
                    Log.i("log_i","onPageSelected position: " + position);
                }
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(titles[position]);
//                Log.i("log_i","onConfigureTab position: " + position);
            }
        });
        tabLayoutMediator.attach();

        tabLayout.removeTabAt(fragmentList.size()-1);
        tabLayout.removeTabAt(0);
        viewpager.setCurrentItem(1);
    }

}