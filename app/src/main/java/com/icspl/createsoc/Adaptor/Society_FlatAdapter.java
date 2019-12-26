package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Model.GetFlatIdModel;
import com.icspl.createsoc.R;

import java.util.List;

public class Society_FlatAdapter extends RecyclerView.Adapter<Society_FlatAdapter.Holder>
{
    onItemFlatClick onItemFlatClick;
    Context context;
    List<GetFlatIdModel> list;
    public Society_FlatAdapter(Context context, List<GetFlatIdModel> list,onItemFlatClick onItemFlatClick) {
        this.context = context;
        this.list = list;
        this.onItemFlatClick=onItemFlatClick;
    }

    @Override
    public Society_FlatAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_grid_flat,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Society_FlatAdapter.Holder holder, final int position) {
                holder.Flat_name.setText(list.get(position).getFlatOwner());
                holder.Flat_number.setText(list.get(position).getFlatNo());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemFlatClick.particularFlatclick(list.get(position).getFlatId());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView Flat_number,Flat_name;
        public Holder(@NonNull View itemView) {
            super(itemView);
            Flat_name=itemView.findViewById(R.id.flat_name);
            Flat_number=itemView.findViewById(R.id.flat_number);

        }
    }

    public interface onItemFlatClick
    {
     void particularFlatclick(String id);
    }

}
