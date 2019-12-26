package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.icspl.createsoc.Model.WingVPModel;
import com.icspl.createsoc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WingVPAdaptor extends RecyclerView.Adapter<WingVPAdaptor.Holder> {
public Context mContext;
public List<WingVPModel> array;
public WingHandler wingHandler;


    public WingVPAdaptor(Context mContext, List<WingVPModel> array,WingHandler wingHandler ) {
        this.mContext = mContext;
        this.array = array;
        this.wingHandler=wingHandler;
    }
    public interface WingHandler{
        void wingnamecallback(Integer pos, String wingname);
    }
    @NonNull
    @Override
    public WingVPAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.row_wingvp,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WingVPAdaptor.Holder holder, final int position) {
        holder.et_wingname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wingHandler.wingnamecallback(position,holder.et_wingname.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public EditText et_wingname;
        public Holder(@NonNull View itemView) {
            super(itemView);

            et_wingname=itemView.findViewById(R.id.et_wingname);
        }
    }
}
