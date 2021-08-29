package com.steph.foodwastagemanagement;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
   ArrayList<Event>mList;


    public MyAdapter(Context context,ArrayList<Event>mList){
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(context).inflate(R.layout.event_list_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event=mList.get(position);
        holder.Location.setText(event.getLocation());
        holder.Date.setText(event.getDate());
        holder.Plates.setText(event.getPlates());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Location,Date,Plates;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            Location=itemView.findViewById(R.id.location);
            Date=itemView.findViewById(R.id.date);
            Plates=itemView.findViewById(R.id.plates);

                }


        }

    }




