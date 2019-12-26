package com.icspl.createsoc.Adaptor;

import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.icspl.createsoc.Activitys.AccountManage;
import com.icspl.createsoc.Activitys.Committee;
import com.icspl.createsoc.Activitys.Complaints;
import com.icspl.createsoc.Activitys.Homescreen;
import com.icspl.createsoc.Activitys.Meetings;
import com.icspl.createsoc.Activitys.Notice;
import com.icspl.createsoc.Activitys.Society_Details;
import com.icspl.createsoc.Model.Homescreenmodel;
import com.icspl.createsoc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptorHOme extends RecyclerView.Adapter<AdaptorHOme.Holder> {
  Context context;
    List<Homescreenmodel> array;

    public AdaptorHOme(Homescreen homescreen, List<Homescreenmodel> array) {
        this.context = homescreen;
        this.array = array;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
      View v = LayoutInflater.from(context).inflate(R.layout.row_adaptorhome,viewGroup,false);
      Holder holder = new Holder(v);
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {

        holder.tv_home.setText( array.get(i).getName());
        holder.img_home.setImageResource(array.get(i).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { holder.getAdapterPosition();
                Toast.makeText(context, ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
               if (holder.getAdapterPosition()==0){
                   Intent intent = new Intent(context, Society_Details.class);
                   context.startActivity(intent);
               } else if (holder.getAdapterPosition()==5) {
                   Intent intent = new Intent(context, AccountManage.class);
                   context.startActivity(intent);
               }else if(holder.getAdapterPosition()==1){
                   Intent intent = new Intent(context, Meetings.class);
                   context.startActivity(intent);
               }
               else if (holder.getAdapterPosition()==2)
               {
                   Intent intent= new Intent(context, Notice.class);
                   context.startActivity(intent);
               }
               else if(holder.getAdapterPosition()==4)
               {
                   Intent intent = new Intent(context, Committee.class);
                   context.startActivity(intent);
               }
               else if(holder.getAdapterPosition()==6)
                {
                    Intent intent= new Intent(context, Complaints.class);
                    context.startActivity(intent);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView img_home;
        private TextView tv_home;

        public Holder(@NonNull View itemView) {

            super(itemView);
            img_home=itemView.findViewById(R.id.img_home);
            tv_home=itemView.findViewById(R.id.tv_home);

        }
    }
}
