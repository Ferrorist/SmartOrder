package com.cookandroid.registerlogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Addmenu extends AppCompatActivity {
    static int restaurant_number;
    private EditText edit_restaurant, edit_name, edit_price, edit_date, edit_explain;
    private Button  button_select, button_add, button_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenu);

        edit_restaurant = findViewById(R.id.edit_restaurant);
        edit_name = findViewById(R.id.edit_name);
        edit_price = findViewById(R.id.edit_price);
        edit_date = findViewById(R.id.edit_date);
        edit_explain = findViewById(R.id.edit_explain);

        button_select = findViewById(R.id.select_restaurant);
        button_add = findViewById(R.id.button_send);
        button_back = findViewById(R.id.button_back);
    }
}
