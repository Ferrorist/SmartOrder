package com.cookandroid.registerlogin;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static ViewPager2 viewpager;
    private List<LoginFragment> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        viewpager = findViewById(R.id.Login_ViewPager);

        list.add(LoginFragment.newInstance("null"));
        list.add(LoginFragment.newInstance("admin"));

        viewpager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) { return list.get(position); }

            @Override
            public int getItemCount() { return list.size(); }
        });
    }

}
