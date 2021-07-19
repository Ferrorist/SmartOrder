package com.cookandroid.registerlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

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


public class UsedTicket extends AppCompatActivity {

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="response";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS ="address";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;
    TextView textView9;
    private Button btn_home;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load2);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://thee153.dothome.co.kr/Ticket.php");

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userNum = intent.getStringExtra("userNum");
        String userTicket = intent.getStringExtra("userTicket");

        textView9 = findViewById(R.id.textView9);
        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");

                Intent intent1 = new Intent(UsedTicket.this, MainActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(UsedTicket.this,
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

            for(int i=0;i<jsonArray.length();i++){


                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");


                JSONObject item = jsonArray.getJSONObject(i);
                String userID2 = item.getString("userID");
                String menu = item.getString("menu");
                String restaurant = item.getString("restaurant");
                String buydate = item.getString("buydate");
                String usecheck = item.getString("usecheck");
                String usedate = item.getString("usedate");

                HashMap<String,String> hashMap = new HashMap<>();

                if(usecheck.equals("YES")&&userID.equals(userID2))
                {
                    hashMap.put(TAG_ID, menu);
                    hashMap.put(TAG_NAME, restaurant);
                    hashMap.put(TAG_ADDRESS, usedate);
                    mArrayList.add(hashMap);
                }


            }

            ListAdapter adapter = new SimpleAdapter(
                    UsedTicket.this, mArrayList, R.layout.item_list7,
                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}



