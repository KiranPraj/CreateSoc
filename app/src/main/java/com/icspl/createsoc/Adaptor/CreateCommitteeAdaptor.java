package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.icspl.createsoc.Model.CreateCommitteeModel;
import com.icspl.createsoc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateCommitteeAdaptor extends RecyclerView.Adapter<CreateCommitteeAdaptor.Holder> {
    public Context mContext;
    public List<CreateCommitteeModel> array;

    public CreateCommitteeAdaptor(Context mContext, List<CreateCommitteeModel> array) {
        this.mContext = mContext;
        this.array = array;
    }

    @NonNull
    @Override
    public CreateCommitteeAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_createcommittee,parent,false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCommitteeAdaptor.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public EditText et_designation;

        public Holder(@NonNull View itemView) {
            super(itemView);
            et_designation=itemView.findViewById(R.id.et_designation);

        }
    }
}
