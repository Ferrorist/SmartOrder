package com.cookandroid.registerlogin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cookandroid.registerlogin.activity.BuyMenuActivity;
import com.cookandroid.registerlogin.R;
import com.cookandroid.registerlogin.activity.UsedTicket;
import com.cookandroid.registerlogin.activity.LoadActivity;
import com.cookandroid.registerlogin.activity.MapsActivity;

public class ClientMainFragment extends Fragment {

    private View view;
    private ImageButton btn_rest,btn_map,btn_ticket,btn_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container,false);

        final ImageButton btn_image = view.findViewById(R.id.button_logout);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        btn_rest    =   view.findViewById(R.id.button_rest);
        btn_map     =   view.findViewById(R.id.button_map);
        btn_ticket  =   view.findViewById(R.id.button_ticket);
        btn_list    =   view.findViewById(R.id.button_uselist);

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

                Intent intent1 = new Intent(getActivity(), BuyMenuActivity.class);
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