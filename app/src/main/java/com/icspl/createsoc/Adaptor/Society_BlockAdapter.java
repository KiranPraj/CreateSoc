package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Model.GetblockidModel;
import com.icspl.createsoc.R;

import java.util.List;

public class Society_BlockAdapter extends RecyclerView.Adapter<Society_BlockAdapter.MyViewHolder> {
    List<GetblockidModel> getblockidModelList;
    Context context;
    AdpterOnItemClick adpterOnItemClick;
    public Society_BlockAdapter(Context context, List<GetblockidModel> getblockidModelList,AdpterOnItemClick adpterOnItemClick) {
        this.context=context;
        this.getblockidModelList=getblockidModelList;
        this.adpterOnItemClick=adpterOnItemClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      holder.name.setText(getblockidModelList.get(position).getBlockName());

   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           //Toast.makeText(context,"hi you are here"+position,Toast.LENGTH_LONG).show();
           adpterOnItemClick.onItemClick(getblockidModelList.get(position).getBlockId(),getblockidModelList.get(position).getBlockName());
       }
   });

    }


    @Override
    public int getItemCount() {
        return getblockidModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;// init the item view's
        public MyViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.block_name);
        }
    }

    public interface AdpterOnItemClick
    {
        void onItemClick(String id,String name);

    }
}
