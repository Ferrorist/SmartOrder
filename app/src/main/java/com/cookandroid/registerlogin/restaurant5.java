package com.cookandroid.registerlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class restaurant5 extends AppCompatActivity {

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="response";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS ="address";
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d"); // 날짜 포맷

    String []data1 = new String [50];
    String []data2 = new String [50];
    String menu;
    String price;
    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;
    TextView textView9,textView13,textView14,textView15,textView17;
    private Button btn_home,btn_buy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant5);

        Date date = new Date();
        String time = mFormat.format(date);

        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();
        textView15 = (TextView) findViewById(R.id.textView15);
        textView17 = (TextView) findViewById(R.id.textView17);

        GetData task = new GetData();
        task.execute("http://thee153.dothome.co.kr/MenuList5.php");

        btn_buy = findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 현재 입력되어 있는 값을 Get해준다
                String Menu = menu;
                String Price = price;
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");
                Date date = new Date();
                String time = mFormat.format(date);
                if (menu.equals(""))
                    Toast.makeText(getApplicationContext(), "메뉴를 선택해 주세요", Toast.LENGTH_SHORT).show();
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jasonObject = new JSONObject(response);
                                boolean success = jasonObject.getBoolean("success");
                                Intent intent = getIntent();
                                String userID = intent.getStringExtra("userID");
                                String userPass = intent.getStringExtra("userPass");
                                String userName = intent.getStringExtra("userName");
                                String userEmail = intent.getStringExtra("userEmail");
                                String userNum = intent.getStringExtra("userNum");
                                String userTicket = intent.getStringExtra("userTicket");
                                if (success) {
                                    Toast.makeText(getApplicationContext(), menu + " " + price + "에 구매 성공!", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(restaurant5.this, MainActivity.class);
                                    intent1.putExtra("userID", userID);
                                    intent1.putExtra("userPass", userPass);
                                    intent1.putExtra("userName", userName);
                                    intent1.putExtra("userNum", userNum);
                                    intent1.putExtra("userEmail", userEmail);
                                    intent1.putExtra("userTicket", userTicket);
                                    startActivity(intent1);
                                } else { //실패한 경우
                                    Toast.makeText(getApplicationContext(), "구매에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    String R = "공학식당1";
                    String usecheck = "NO";
                    String usedate = "";
                    int num = (int)(Math.random() * 100000000 - 1);
                    String barcode = num + "";
                    MenuRequest menuRequest = new MenuRequest(userID, Menu, Price, R, time,usedate, usecheck,barcode, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(restaurant5.this);
                    queue.add(menuRequest);
                    Count5Request countRequest = new Count5Request(menu,time, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(restaurant5.this);
                    queue1.add(countRequest);

                }
            }
        });

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
//                Intent intent = getIntent();
//                String userID = intent.getStringExtra("userID");
//                String userPass = intent.getStringExtra("userPass");
//                String userName = intent.getStringExtra("userName");
//                String userEmail = intent.getStringExtra("userEmail");
//                String userNum = intent.getStringExtra("userNum");
//                String userTicket = intent.getStringExtra("userTicket");
//
//                Intent intent1 = new Intent(restaurant5.this, MainActivity.class);
//                intent1.putExtra("userID",userID);
//                intent1.putExtra("userPass",userPass);
//                intent1.putExtra("userName",userName);
//                intent1.putExtra("userNum",userNum);
//                intent1.putExtra("userEmail",userEmail);
//                intent1.putExtra("userTicket", userTicket);
//                startActivity(intent1);
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(restaurant5.this,
                    "Please Wait", null, true, true);
        }




        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

            if (result == null){

                Toast.makeText(getApplicationContext(),"조회할 자료가 없습니다.",Toast.LENGTH_SHORT).show();
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


                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");


                JSONObject item = jsonArray.getJSONObject(i);
                String menu = item.getString("menu");
                String date2 = item.getString("date");
                String price = item.getString("price");
                String note = item.getString("note");

                HashMap<String,String> hashMap = new HashMap<>();
                if(date2.equals(time)) { //&& date2.equals(time)
                    hashMap.put(TAG_ID, menu);
                    hashMap.put(TAG_NAME, price);
                    hashMap.put(TAG_ADDRESS, note);
                    data1[i]=menu;
                    data2[i]=price;
                    mArrayList.add(hashMap);
                }


            }

            ListAdapter adapter = new SimpleAdapter(
                    restaurant5.this, mArrayList, R.layout.item_list5,
                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );
            restaurant5.ClickListener listener = new restaurant5.ClickListener();
            mlistView.setOnItemClickListener(listener);
            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    class ClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            textView15.setText(data1[position]);
            menu=data1[position];
            textView17.setText(data2[position]);
            price=data2[position];

        }
    }


}

