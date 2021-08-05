package com.cookandroid.registerlogin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cookandroid.registerlogin.activity.ChangePassActivity;
import com.cookandroid.registerlogin.R;
import com.cookandroid.registerlogin.activity.ChangeEmailActivity;
import com.cookandroid.registerlogin.activity.DeleteIdActivity;

public class UserInfoFragment extends Fragment {
        private View view;
        private TextView tv_id,tv_name,tv_Num,tv_Email;
        private Button btn_emailch,btn_passch,btn_deleteID;
        String userID, userPass, userName, userNum, userEmail, loginmode, userTicket;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.activity_userinfo, container, false);

            tv_id = view.findViewById(R.id.text_infoID);
            tv_name = view.findViewById(R.id.text_infoName);
            tv_Num = view.findViewById(R.id.text_infoNumber);
            tv_Email = view.findViewById(R.id.text_infoEmail);

            Bundle bundle = getArguments();
            userID = bundle.getString("userID");
            userPass = bundle.getString("userPass");
            userName = bundle.getString("userName");
            userNum = bundle.getString("userNum");
            userEmail = bundle.getString("userEmail");
            userTicket = bundle.getString("userTicket");
            loginmode = bundle.getString("loginmode");

        tv_id.setText(userID);
        tv_name.setText(userName);
        tv_Num.setText(userNum);
        tv_Email.setText(userEmail);


        btn_deleteID = view.findViewById(R.id.btn_deleteID);
        btn_deleteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity(), DeleteIdActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_passch = view.findViewById(R.id.button_changePass);
        btn_passch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity(), ChangePassActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                intent1.putExtra("loginmode",loginmode);
                startActivity(intent1);
            }
        });


        btn_emailch = view.findViewById(R.id.button_changeEmail);
        btn_emailch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity(), ChangeEmailActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                intent1.putExtra("loginmode",loginmode);
                startActivity(intent1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
