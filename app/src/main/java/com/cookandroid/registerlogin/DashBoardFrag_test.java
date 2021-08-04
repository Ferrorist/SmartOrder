package com.cookandroid.registerlogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashBoardFrag_test extends Fragment {
    ImageButton selectDateButton;
    TextView    selectedDate;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soldlist2,container,false);
        selectDateButton = view.findViewById(R.id.calendar_button);
        selectedDate = view.findViewById(R.id.Date_text);
        return view;
    }
}
