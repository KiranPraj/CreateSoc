package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.icspl.createsoc.Model.BlockVPModel;
import com.icspl.createsoc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlockVPAdaptor extends RecyclerView.Adapter<BlockVPAdaptor.Holder> {
  public Context mContext;
  public List<BlockVPModel> array;
  private Blockhandler blockhandler;

    public BlockVPAdaptor(Context mContext, List<BlockVPModel> array,Blockhandler blockhandler) {
        this.mContext = mContext;
        this.array = array;
        this.blockhandler=blockhandler;
    }
    public interface Blockhandler{
        void blocknamecallback(Integer pos, String blockName);
    }

    @NonNull
    @Override
    public BlockVPAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_blockvp,parent,false);
        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BlockVPAdaptor.Holder holder, final int position) {
//        holder.et_blockname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId== EditorInfo.IME_ACTION_DONE)
//                {
//                    blockhandler.blocknamecallback(position,v,holder.et_blockname.getText().toString());
//                }
//                return false;
//            }
//        });
//        holder.et_blockname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                blockhandler.blocknamecallback(position,v,holder.et_blockname.getText().toString());
//            }
//        });
        holder.et_blockname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                blockhandler.blocknamecallback(position,holder.et_blockname.getText().toString());
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
        public EditText et_blockname;
        public Holder(@NonNull View itemView) {
            super(itemView);
            et_blockname=itemView.findViewById(R.id.et_blockname);


        }
    }
}
