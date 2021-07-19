package com.cookandroid.registerlogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Ticket extends AppCompatActivity {
    private TextView tv_select;
    private TextView whenDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/M/d"); // 날짜 포맷

    String [] data1={
            "돈카츠벤또","수제소세지카레덮밥","오삼비빔밥","비건스테이크","일반돈가스","통등심돈가스"
            ,"통등심치즈돈가스","고구마치즈돈가스","왕돈가스","어묵우동"};
    String [] data2 ={
            "정보센터","감꽃푸드","공학식당1","공학식당2","정보센터","복지관","첨성","공학식당1","첨성","복지관"
    };

    String menu;
    String price;
    String usecheck = "NO";
    String restaurant ="restaurant1";
    ListView list1;
    TextView textView1,textView2,textView3,textView4,textView5;
    private Button btn_refresh,btn_home;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Date date = new Date();
        String time = mFormat.format(date);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userNum = intent.getStringExtra("userNum");
        String userTicket = intent.getStringExtra("userTicket");

        list1 = (ListView) findViewById(R.id.list);
        textView1 = (TextView) findViewById(R.id.textView1);


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

                Intent intent1 = new Intent(Ticket.this, MainActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        ArrayList<HashMap<String,String>> data_list = new ArrayList<HashMap<String,String>>();
        //ArrayList에 데이터를 담는다
        for(int i=0; i<data1.length; i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("str1",data1[i]);
            map.put("str2",data2[i]);

            data_list.add(map);
        }

        String [] keys ={"str1","str2"};
        int [] ids = {android.R.id.text1,android.R.id.text2};
        //어뎁터를 리스트뷰에 셋팅
        SimpleAdapter adapter = new SimpleAdapter(this,data_list,android.R.layout.simple_list_item_2,keys,ids);

        list1.setAdapter(adapter);

        //리스너를 셋팅한다
        ClickListener listener = new ClickListener();
        list1.setOnItemClickListener(listener);
    }
    class ClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            menu=data1[position];
            price=data2[position];

            String text2Qr = data1[position]+data2[position];
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                Intent intent = new Intent(context, QrActivity.class);
                intent.putExtra("pic",bitmap);
                context.startActivity(intent);
            } catch (WriterException e) {
                e.printStackTrace();
            }

        }
    }






    }
