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

public class Frag2 extends Fragment {

    private View view;
    private Button btn_ticket,btn_res1,btn_res2,btn_res3,btn_res4,btn_res5,btn_res6,btn_test,btn_res7,btn_res8,btn_res9;

    String [] data1 = new String[50];
    String [] data2 = new String[50];
    String [] data3 = new String[50];
    String [] data4 = new String[50];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_frag1, container,false);



        final ImageButton btn_image = (ImageButton)view.findViewById(R.id.button_logout);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(),LoginActivity.class);    startActivity(intent);
                getActivity().finish();
            }
        });

        final ImageButton btn_res1 = (ImageButton)view.findViewById(R.id.button_rest1);
        final ImageButton btn_res2 = (ImageButton)view.findViewById(R.id.button_rest2);
        final ImageButton btn_res3 = (ImageButton)view.findViewById(R.id.button_rest3);
        final ImageButton btn_res4 = (ImageButton)view.findViewById(R.id.button_rest4);
        final ImageButton btn_res5 = (ImageButton)view.findViewById(R.id.button_rest5);
        final ImageButton btn_res6 = (ImageButton)view.findViewById(R.id.button_rest6);
        final ImageButton btn_res7 = (ImageButton)view.findViewById(R.id.button_ticket);
        final ImageButton btn_res8 = (ImageButton)view.findViewById(R.id.button_uselist);
        final ImageButton btn_res9 = (ImageButton)view.findViewById(R.id.button_map);
        btn_res1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), restaurant1.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });
        btn_res2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), restaurant2.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });
        btn_res3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), restaurant3.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });
        btn_res4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), restaurant4.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });
        btn_res5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), restaurant5.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });
        btn_res6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String userID = bundle.getString("userID");
                String userPass = bundle.getString("userPass");
                String userName = bundle.getString("userName");
                String userNum = bundle.getString("userNum");
                String userEmail = bundle.getString("userEmail");
                String userTicket = bundle.getString("userTicket");

                Intent intent1 = new Intent(getActivity(), restaurant6.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);


            }
        });

        btn_res7.setOnClickListener(new View.OnClickListener() {
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

        btn_res8.setOnClickListener(new View.OnClickListener() {
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

        btn_res9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent1);
            }
        });

        return view;
    }


}
