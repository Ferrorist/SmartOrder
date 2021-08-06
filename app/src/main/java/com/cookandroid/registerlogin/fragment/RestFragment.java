package com.cookandroid.registerlogin.fragment;

import android.app.ProgressDialog;
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

import com.cookandroid.registerlogin.request.CountRequest;
import com.cookandroid.registerlogin.request.MenuRequest;
import com.cookandroid.registerlogin.R;
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

public class RestFragment extends Fragment {

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="response";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS ="address";
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d"); // 날짜 포맷
    private List<String> MenuData = new ArrayList<>();
    private List<String> PriceData = new ArrayList<>();
    String menu = "null";
    String price = "null";
    String php_address = null;
    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    ListView mlistView;
    String mJsonString;
    TextView rest_name,menuname,pricetext;
    private Button btn_buy;


    private static String input_number = "-1";
    private static String input_rest_name = "";
    private static String input_name = "";


    private String restaurant_number;
    private String restaurant_name;
    private String user_ID;

    public RestFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            restaurant_number = getArguments().getString(input_number);
            user_ID = getArguments().getString(input_name);
            restaurant_name = getArguments().getString(input_rest_name);
        }
    }

    public static RestFragment newInstance(String param1, String param2, String param3) {
        RestFragment fragment = new RestFragment();
        Bundle args = new Bundle();
        args.putString(input_number, param1);
        args.putString(input_name, param2);
        args.putString(input_rest_name, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_restaurant, container, false);
        mlistView = view.findViewById(R.id.listView_main_list);
        rest_name = view.findViewById(R.id.restaurant_name);
        menuname = view.findViewById(R.id.menu_name);
        pricetext = view.findViewById(R.id.price_text);
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
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(menuRequest);

                    CountRequest countRequest = new CountRequest(menu,time,restaurant_number,responseListener);    //  새로운 Request와 php 파일 필요.
                    RequestQueue queue1 = Volley.newRequestQueue(getContext());
                    queue1.add(countRequest);
                }
            }
        });
        rest_name.setText(restaurant_name);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        MenuData.clear();   PriceData.clear();  mArrayList.clear();
    }

    private void Execute() {
        if(!user_ID.equals("")) {
            GetData task = new GetData();
//            php_address = "http://thee153.dothome.co.kr/MenuList" + restaurant_number + ".php"; //  개선하면 좋을듯.
            php_address = "http://thee153.dothome.co.kr/testMenuList.php?rest_name=" + restaurant_name;
            task.execute(php_address);
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(),
                    "잠시만 기다려주세요...", null, true, true);
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
            HttpURLConnection httpURLConnection;
            try {
                URL url = new URL(serverURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();

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
                httpURLConnection.disconnect();
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
            Log.i("test","mJsonString : " + mJsonString);
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
                if(date2.equals(time)){
                    hashMap.put(TAG_ID, menu);
                    hashMap.put(TAG_NAME, price);
                    hashMap.put(TAG_ADDRESS, note);
                    MenuData.add(menu);
                    PriceData.add(price);
                    mArrayList.add(hashMap);
                }
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(),
                    mArrayList,
                    R.layout.item_list,
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
            menuname.setText(MenuData.get(position));
            menu= menuname.getText().toString();
            pricetext.setText(PriceData.get(position));
            price= pricetext.getText().toString();
        }
    }
}