package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Model.DtCoOwnerDetailsModel;
import com.icspl.createsoc.R;

import java.util.List;

public class CoOwnerDetailsAdaptor extends RecyclerView.Adapter<CoOwnerDetailsAdaptor.Holder> {
    Context context;
    List<DtCoOwnerDetailsModel> array;

    public CoOwnerDetailsAdaptor(Context context, List<DtCoOwnerDetailsModel> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public CoOwnerDetailsAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_co_owner_details,parent,false);
        Holder holder= new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoOwnerDetailsAdaptor.Holder holder, int position) {
        if(!array.get(position).getOwner_name().equals("")&&!array.get(position).getOwner_name().equals(null))
        {
            holder.co_owner_name.setText(array.get(position).getOwner_name());
        }
        if(!array.get(position).getOwner_address().equals("")&&!array.get(position).getOwner_address().equals(null))
        {
            holder.co_owner_address.setText(array.get(position).getOwner_address());
        }
        if(!array.get(position).getOwner_adharcard().equals("")&&!array.get(position).getOwner_adharcard().equals(null))
        {
            holder.co_owner_adharcard.setText(array.get(position).getOwner_adharcard());
        }
        if(!array.get(position).getOwner_dob().equals("")&&!array.get(position).getOwner_dob().equals(null))
        {
            holder.co_owner_dob.setText(array.get(position).getOwner_dob());

        }
        if(!array.get(position).getOwner_email().equals("")&&!array.get(position).getOwner_email().equals(null))
        {
            holder.co_owner_email.setText(array.get(position).getOwner_email());
        }
        if(!array.get(position).getOwner_mobile().equals("")&&!array.get(position).getOwner_mobile().equals(null))
        {
            holder.co_owner_mobileno.setText(array.get(position).getOwner_mobile());
        }
        if(!array.get(position).getOwner_pancard().equals("")&&!array.get(position).getOwner_pancard().equals(null))
        {
            holder.co_owner_pancard.setText(array.get(position).getOwner_pancard());
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView co_owner_name,co_owner_pancard,co_owner_adharcard,co_owner_dob,co_owner_address,co_owner_mobileno,co_owner_email;
        public Holder(@NonNull View itemView) {
            super(itemView);
            co_owner_name= itemView.findViewById(R.id.co_owner_name);
            co_owner_pancard= itemView.findViewById(R.id.co_owner_pancard);
            co_owner_adharcard= itemView.findViewById(R.id.co_owner_adharcard);
            co_owner_dob= itemView.findViewById(R.id.co_owner_dob);
            co_owner_address= itemView.findViewById(R.id.co_owner_address);
            co_owner_mobileno= itemView.findViewById(R.id.co_owner_mobileno);
            co_owner_email= itemView.findViewById(R.id.co_owner_email);
        }
    }
}
