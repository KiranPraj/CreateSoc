package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Model.DtSocietyProfileModel;
import com.icspl.createsoc.R;

import java.util.List;

public class Society_ProfileAdapter extends RecyclerView.Adapter<Society_ProfileAdapter.Holder>
{
    Context context;

    public Society_ProfileAdapter(Context context, List<DtSocietyProfileModel> array) {
        this.context = context;
        this.array = array;
    }

    List<DtSocietyProfileModel> array;


    @NonNull
    @Override
    public Society_ProfileAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_society_profile,parent,false);
        Holder  holder= new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Society_ProfileAdapter.Holder holder, int position) {
        holder.rvtv_title.setText(array.get(position).getStoreName());
        holder.rvtv_desc.setText(array.get(position).getStoreAddress());
        if(array.get(position).getSrNo().equals(null))
        {
            holder.rvbtn_photo.setVisibility(View.GONE);
        }
        if(array.get(position).getStoreName().equals(null))
        {
            holder.rvbtn_file.setVisibility(View.GONE);
        }
        holder.rvbtn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.rvbtn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        AppCompatTextView rvtv_title,rvtv_desc;
        Button rvbtn_file,rvbtn_photo;
        public Holder(@NonNull View itemView) {
            super(itemView);
            rvtv_title= itemView.findViewById(R.id.rvtv_title);
            rvtv_desc=itemView.findViewById(R.id.rvtv_Value);
            rvbtn_file=itemView.findViewById(R.id.rvbtn_file);
            rvbtn_photo=itemView.findViewById(R.id.rvbtn_cam);

        }
    }
}
