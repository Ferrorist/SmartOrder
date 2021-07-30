package com.cookandroid.registerlogin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class QrReader extends AppCompatActivity {
    Button btnScan, btnPrint;
    TextView tvTest;
    ImageView ivTest;
    static String string;
    String Barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        tvTest = (TextView)findViewById(R.id.textview_test);
        ivTest = (ImageView)findViewById(R.id.imageview_test);
        btnScan = (Button)findViewById(R.id.button_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(QrReader.this).initiateScan();
            }
        });
        btnPrint = (Button)findViewById(R.id.button_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTest.setText(string);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = null;

                try {
                    // 지정한 번호,포맷,사이즈로 바코드 만들기
                    bitmap = barcodeEncoder.encodeBitmap(string, BarcodeFormat.UPC_A,200,20);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                ivTest.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IntentIntegrator.REQUEST_CODE){
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null) {
                    if (result.getContents() != null) {
                        Toast.makeText(this, "Scanned: " + result.getContents() + "\nFormat:" + result.getFormatName(), Toast.LENGTH_LONG).show();
                        string = result.getContents();
                        Barcode = result.getContents();
                        Response.Listener<String> responseListener =new Response.Listener<String>() { //barcode 유효성 검사.
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jasonObject = new JSONObject(response);
                                    boolean success = jasonObject.getBoolean("success");
                                    if(success){
                                        String barcode = jasonObject.getString("barcode");
                                        String usecheck = jasonObject.getString("usecheck");

                                        if(barcode.equals(Barcode)&&usecheck.equals("NO"))
                                        {
                                            Toast.makeText(getApplicationContext(),"유효한 식권입니다! ",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"유효하지 않은 식권입니다..",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }else{ //실패한 경우
                                        Toast.makeText(getApplicationContext(),"유효하지 않은 식권입니다..",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        };
                        VerifyTicketRequest verify = new VerifyTicketRequest(Barcode, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(QrReader.this);
                        queue.add(verify);
                    }
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}