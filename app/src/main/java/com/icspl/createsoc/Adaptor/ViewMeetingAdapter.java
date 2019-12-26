package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.Activitys.ViewMeeting;
import com.icspl.createsoc.Model.ViewMeetingModel;
import com.icspl.createsoc.R;

import java.util.List;

public class ViewMeetingAdapter extends RecyclerView.Adapter<ViewMeetingAdapter.Holder>
{
    Context mContext;
    public List<ViewMeetingModel> array;
    public ViewMeetingAdapter(ViewMeeting mContext, List<ViewMeetingModel> array) {
        this.mContext = mContext;
        this.array = array;

    }
   public void setArray(List<ViewMeetingModel> array)
   {
       this.array=array;
       notifyDataSetChanged();
   }
    @NonNull
    @Override
    public ViewMeetingAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.row_adaptorviewemeeting,parent,false);
      Holder holder = new Holder(view);
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMeetingAdapter.Holder holder, int position)
    {
    holder.tv_discription.setText(array.get(position).meDesc);
    holder.tv_type.setText(array.get(position).meType);
    holder.tv_meetingdate.setText((array.get(position).me_date).substring(0,10));


    }

    @Override
    public int getItemCount() {
        return array.size();
    }
    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_discription,tv_type,tv_meetingdate;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_meetingdate=itemView.findViewById(R.id.tv_meetingdate);
            tv_discription=itemView.findViewById(R.id.tv_discription);
            tv_type=itemView.findViewById(R.id.tv_meetingtype);

        }
    }
}
