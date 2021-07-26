package com.cookandroid.registerlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class RestFragment extends Fragment {

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="response";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS ="address";
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d"); // 날짜 포맷

    String []data1 = new String [50];
    String []data2 = new String [50];
    String menu = "null";
    String price = "null";
    String php_address = null;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;
    TextView rest_name,menuname,pricetext;
    private Button btn_buy;


    private static String input_number = "1";
    private static String input_name = "";
    private String restaurant_number;
    private String user_ID;
    public RestFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            restaurant_number = getArguments().getString(input_number);
            user_ID = getArguments().getString(input_name);
        }
    }

    public static RestFragment newInstance(String param1, String param2) {
        RestFragment fragment = new RestFragment();
        Bundle args = new Bundle();
        args.putString(input_number, param1);
        args.putString(input_name, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurant, container, false);
        Date date = new Date();
        String time = mFormat.format(date);

        mlistView = (ListView) view.findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();
        rest_name = (TextView) view.findViewById(R.id.restaurant_name);
        menuname = (TextView) view.findViewById(R.id.menu_name);
        pricetext = (TextView) view.findViewById(R.id.price_text);
        btn_buy = view.findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 현재 입력되어 있는 값을 Get해준다
                String Menu = menu;
                String Price = price;
                Date date = new Date();
                String time = mFormat.format(date);
                if (Menu == null || Menu.isEmpty() || Menu.equals("null"))
                    Toast.makeText(getContext(), "메뉴를 선택해 주세요", Toast.LENGTH_SHORT).show();
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject = new JSONObject(response);
                                boolean success = jasonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getContext(), menu + " " + price + "원에 구매 성공!", Toast.LENGTH_SHORT).show();
                                } else { //실패한 경우
                                    Toast.makeText(getContext(), "구매에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    String R = rest_name.getText().toString();
                    String usecheck = "NO";
                    String usedate = "";
                    int num = (int)(Math.random() * 100000000 - 1);
                    String barcode = num + "";
                    MenuRequest menuRequest = new MenuRequest(user_ID, Menu, Price, R, time,usedate, usecheck,barcode, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());  // ★
                    queue.add(menuRequest);

                    Count1Request countRequest = new Count1Request(menu,time, responseListener); // ★
                    RequestQueue queue1 = Volley.newRequestQueue(getContext()); // ★
                    queue1.add(countRequest);
                }
            }
        });
        Execute();
        return view;
    }

    private void Execute() {
        switch(restaurant_number){
            case "1":
                rest_name.setText("정보센터식당"); break;
            case "2":
                rest_name.setText("복지관 교직원식당"); break;
            case "3":
                rest_name.setText("카페테리아 첨성"); break;
            case "4":
                rest_name.setText("GP감꽃푸드코트"); break;
            case "5":
                rest_name.setText("공학식당1"); break;
            case "6":
                rest_name.setText("공학식당2"); break;
        }
        GetData task = new GetData();
        php_address = "http://thee153.dothome.co.kr/MenuList" + restaurant_number + ".php";
        task.execute(php_address);
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){
                Toast.makeText(getContext(),"조회할 자료가 없습니다.",Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString().trim();


            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            Date date = new Date();
            String time = mFormat.format(date);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String menu = item.getString("menu");
                String date2 = item.getString("date");
                String price = item.getString("price");
                String note = item.getString("note");

                HashMap<String,String> hashMap = new HashMap<>();
                if(date2.equals(time)){ //&& date2.equals(time)
                    hashMap.put(TAG_ID, menu);
                    hashMap.put(TAG_NAME, price);
                    hashMap.put(TAG_ADDRESS, note);
                    data1[i]=menu;
                    data2[i]=price;
                    mArrayList.add(hashMap);
                }
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );
            ClickListener listener = new ClickListener();
            mlistView.setOnItemClickListener(listener);
            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    class ClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            menuname.setText(data1[position]);
            menu=data1[position];
            pricetext.setText(data2[position]);
            price=data2[position];
        }
    }
}