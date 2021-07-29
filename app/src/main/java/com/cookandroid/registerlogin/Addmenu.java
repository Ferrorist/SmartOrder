package com.cookandroid.registerlogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Addmenu extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private String restaurant_number="1";
    private EditText edit_name, edit_price, edit_date, edit_explain;
    private Button  button_add, button_back;
    private Spinner menu_spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenu);

        edit_name = findViewById(R.id.edit_name);
        edit_price = findViewById(R.id.edit_price);
        edit_date = findViewById(R.id.edit_date);
        edit_explain = findViewById(R.id.edit_explain);

        menu_spinner = findViewById(R.id.menu_spinner);

        button_add = findViewById(R.id.button_send);
        button_back = findViewById(R.id.button_back);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.restaurant_name,android.R.layout.simple_spinner_dropdown_item);
        menu_spinner.setAdapter(adapter);

        menu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                restaurant_number = Integer.toString(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menu = edit_name.getText().toString();
                String price = Integer.toString(Integer.parseInt(edit_price.getText().toString()));
                String date = edit_date.getText().toString();
                String note = edit_explain.getText().toString();

                if(edit_name.equals("") || edit_price.equals("") || edit_date.equals("")){
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요. (이름/가격/날짜)",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    edit_name.setText("");  edit_price.setText("");
                                    edit_date.setText("");  edit_explain.setText("");
                                    builder = new AlertDialog.Builder(Addmenu.this);
                                    builder.setMessage("등록 성공!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
//                                    Toast.makeText(Addmenu.this,"등록 성공!",Toast.LENGTH_LONG).show();s
                                } else {
                                    builder = new AlertDialog.Builder(Addmenu.this);
                                    builder.setMessage("등록 실패...").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
//                                    Toast.makeText(Addmenu.this,"등록 실패...",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AddMenuRequest validateRequest = new AddMenuRequest(menu,price,date,note,"0",restaurant_number, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Addmenu.this);
                    queue.add(validateRequest);
                }
            }
        });
    }
}
