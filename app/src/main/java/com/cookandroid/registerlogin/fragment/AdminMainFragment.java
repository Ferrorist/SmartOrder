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
import com.cookandroid.registerlogin.activity.QrReader;
import com.cookandroid.registerlogin.R;
import com.cookandroid.registerlogin.activity.Addmenu;
import com.cookandroid.registerlogin.activity.DashBoardActivity;

public class AdminMainFragment extends Fragment {
    ImageButton dashborad_button, addmenu_button, reader_button, logout_button;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_main,container,false);

        logout_button = view.findViewById(R.id.button_logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        dashborad_button = view.findViewById(R.id.btn_dashboard);
        addmenu_button = view.findViewById(R.id.btn_addmenu);
        reader_button = view.findViewById(R.id.btn_QRreader);

        dashborad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                startActivity(intent);
            }
        });

        addmenu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Addmenu.class);
                startActivity(intent);
            }
        });

        reader_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QrReader.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
