package com.cookandroid.registerlogin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DashBoardFragment extends Fragment {
    private static final String TAG_JSON="response";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS ="address";

    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d"); // 날짜 포맷

    private List<String> MenuData = new ArrayList<>();
    private List<String> PriceData = new ArrayList<>();
    private List<String> NumData = new ArrayList<>();

    String menu = "null";
    String price = "null";
    String php_address = null;
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    ListView mlistView;
    String mJsonString;
    private Button btn_home;

    private static String input_number = "1";
    private static String restaurant_number;

    public DashBoardFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            restaurant_number = getArguments().getString(input_number);
        }
        LookData();
    }

    public static DashBoardFragment newInstance(String param1) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putString(input_number, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sold_list, container, false);
        btn_home = view.findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mlistView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mArrayList.size() == 0) LookData();
        setListAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
        mArrayList.clear();
    }

    void setListAdapter() {
        ListAdapter adapter = new SimpleAdapter(
                getContext(), mArrayList, R.layout.item_list1,
                new String[]{TAG_ID, TAG_NAME, TAG_ADDRESS},
                new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
        );
        mlistView.setAdapter(adapter);
    }

    public void LookData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        String menu = item.getString("menu");
                        String date2 = item.getString("date");
                        String price = item.getString("price");
                        String note = item.getString("note");
                        String num = item.getString("num");

                        HashMap<String,String> hashMap = new HashMap<>();
                        MenuData.add(menu);
                        PriceData.add(price);
                        NumData.add(num);
                        hashMap.put(TAG_ID, menu);
                        hashMap.put(TAG_NAME, num);
                        hashMap.put(TAG_ADDRESS, date2);
                        mArrayList.add(hashMap);
                    }
                }   catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        DashBoardRequest Request = new DashBoardRequest(restaurant_number, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(Request);
    }
}