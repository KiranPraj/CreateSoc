package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Model.DtSocietyProfileModel;
import com.icspl.createsoc.R;

import java.util.List;

public class GetOwnerDocumentAdaptor extends RecyclerView.Adapter<GetOwnerDocumentAdaptor.Holder> {
    Context context;

    public GetOwnerDocumentAdaptor(Context context, List<DtSocietyProfileModel> array) {
        this.context = context;
        this.array = array;
    }

    List<DtSocietyProfileModel> array;
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_society_profile,parent,false);
        Holder holder= new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
