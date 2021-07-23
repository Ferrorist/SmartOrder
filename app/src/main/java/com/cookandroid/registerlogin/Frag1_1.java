package com.cookandroid.registerlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Frag1_1 extends Fragment {

    private View view;
    private ImageButton btn_rest,btn_map,btn_ticket,btn_list;

    String [] data1 = new String[50];
    String [] data2 = new String[50];
    String [] data3 = new String[50];
    String [] data4 = new String[50];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_1_1, container,false);

        final ImageButton btn_image = (ImageButton)view.findViewById(R.id.button_logout);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        btn_rest = (ImageButton)view.findViewById(R.id.button_rest);
        btn_map = (ImageButton)view.findViewById(R.id.button_map);
        btn_ticket = (ImageButton) view.findViewById(R.id.button_ticket);
        btn_list = (ImageButton) view.findViewById(R.id.button_uselist);
        final ImageButton btn_res1 = (ImageButton)view.findViewById(R.id.button_rest1);

        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), testActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), LoadActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), UsedTicket.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent1);
            }
        });

        return view;
    }


}
