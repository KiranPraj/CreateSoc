package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.icspl.createsoc.Model.FlatVPModel;
import com.icspl.createsoc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FlatVPAdaptor extends RecyclerView.Adapter<FlatVPAdaptor.Holder> {
    public Context mContext;
    public List<FlatVPModel> array;
    public Flathandler flathandler;

    public FlatVPAdaptor(Context mContext, List<FlatVPModel> array,Flathandler flathandler) {
        this.mContext = mContext;
        this.array = array;
        this.flathandler=flathandler;
    }
    public interface Flathandler{
        void flatname(Integer pos,String name);
        void flatnumber(Integer pos,String number);
        void flatnoofoccupants(Integer pos,String noofoccupants);
        void flatbuilduparea(Integer pos,String builduparea);
        void flatcarpetarea(Integer pos,String carpetarea);

    }

    @NonNull
    @Override
    public FlatVPAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_flatvp,parent,false);
        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FlatVPAdaptor.Holder holder, final int position) {
        holder.et_flatkno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flathandler.flatnumber(position,holder.et_flatkno.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.et_flatname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flathandler.flatname(position,holder.et_flatname.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.et_noofoccupents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flathandler.flatnoofoccupants(position,holder.et_noofoccupents.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.et_builuparea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flathandler.flatbuilduparea(position,holder.et_builuparea.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.et_carpetarea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flathandler.flatcarpetarea(position,holder.et_carpetarea.getText().toString());
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
        public EditText et_flatname,et_flatkno,et_noofoccupents,et_builuparea,et_carpetarea;
        public Holder(@NonNull View itemView) {
            super(itemView);
            et_flatname=itemView.findViewById(R.id.et_flatname);
            et_flatkno=itemView.findViewById(R.id.et_flatkno);
            et_noofoccupents=itemView.findViewById(R.id.et_noofoccupents);
            et_builuparea=itemView.findViewById(R.id.et_builuparea);
            et_carpetarea=itemView.findViewById(R.id.et_carpetarea);

        }
    }
}
