package com.cookandroid.registerlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class BuyMenuAdapter extends RecyclerView.Adapter<BuyMenuAdapter.ViewHolder> {
    private List<RestFragment> list = new ArrayList<>();

    public BuyMenuAdapter(List<RestFragment> list){
        this.list = list;
    }

    @NonNull
    @Override
    public BuyMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_restaurant,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyMenuAdapter.ViewHolder holder, int position) {
        RestFragment fragment = list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
