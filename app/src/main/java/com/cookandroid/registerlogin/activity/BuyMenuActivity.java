  package com.cookandroid.registerlogin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cookandroid.registerlogin.R;
import com.cookandroid.registerlogin.fragment.RestFragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuyMenuActivity extends AppCompatActivity {
    private List<RestFragment> fragmentList = new ArrayList<>();
    private static final String TAG_JSON="response";
    private static String TAG = "phptest_MainActivity";
    private ViewPager2 viewpager;
    private TabLayout tabLayout;
    private String userID;
    String mJsonString;
    // 식당 이름.
    private List<String> testList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buymenu);
        GetData task = new GetData();
        task.execute("http://thee153.dothome.co.kr/RestList.php/");
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
    }

    private void initView() {
        viewpager = findViewById(R.id.ViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        Log.d(TAG, "testList.size() = " + testList.size());
        if(testList.size() > 0){
            fragmentList.add(RestFragment.newInstance(Integer.toString(testList.size()),"",testList.get(testList.size()-1)));
            for(int i = 1; i <= testList.size(); i++)
                fragmentList.add(RestFragment.newInstance(Integer.toString(i), userID, testList.get(i-1)));
            fragmentList.add(RestFragment.newInstance("1","",testList.get(0)));
        }

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

        for(int i = 0; i < testList.size(); i++){
            tabLayout.addTab(tabLayout.newTab().setText(testList.get(i)));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { // tab 선택 시 호출됨.
                int pos = tab.getPosition();
                viewpager.setCurrentItem(pos+1,false); // 선택된 tab에 맞춰 viewpager 이동.
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


    private class GetData extends AsyncTask<String, Void, String> {
        String errorString = null;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){
                Toast.makeText(BuyMenuActivity.this,"조회할 자료가 없습니다.",Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                ApplyList();
                initView();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            HttpURLConnection httpURLConnection;
            try {
                URL url = new URL(serverURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();
                Log.d(TAG, "connect success");
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                Log.d(TAG, "success if-else");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                Log.d(TAG, "start while");
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                Log.d(TAG, "finish while");
                bufferedReader.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    void ApplyList(){
        Log.i("test","mJsonString = " + mJsonString);
        if(mJsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                Log.i("test", "try add List");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    String name = item.getString("name");
                    testList.add(name);
                    Log.i("test", "success add List : " + name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}