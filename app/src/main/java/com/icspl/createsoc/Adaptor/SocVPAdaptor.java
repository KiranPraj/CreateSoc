package com.icspl.createsoc.Adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.icspl.createsoc.Model.SocVPModel;
import com.icspl.createsoc.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SocVPAdaptor extends RecyclerView.Adapter<SocVPAdaptor.Holder> {

   // public static Object Photoclickhandler;
    private Photoclickhandler photoclickhandler;
    public Context mContext;
    public List<SocVPModel> array;
    //public Photoclickhandler photoclickhandler;
//
//    public interface hotolistener{
//        void choosephoto(View view,int position);
//
//    }
    public SocVPAdaptor(Context mContext, List<SocVPModel> array, Photoclickhandler photoclickhandler) {
        this.mContext = mContext;
        this.array = array;
        this.photoclickhandler = photoclickhandler;
    }
    public interface Photoclickhandler{
        void photoclickcallback(Integer pos,View v,Button rvbtn_cam);
        void documentclickcallback(Integer pos ,View v,Button rvbtn_file);
        void valuecallback(Integer pos,String titile,String value );
    }

    @NonNull
    @Override
    public SocVPAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_socvpadapt,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SocVPAdaptor.Holder holder, final int position) {
//String val=holder.rvEt_title.getText(array.get(position).getEttitle()).toString();


        holder.rvbtn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      SocietyDetailsVP societyDetailsVP = new SocietyDetailsVP();
//                societyDetailsVP.num=position+8;
            //   societyDetailsVP.test=holder.rvbtn_cam;
//                societyDetailsVP.pickimage();
                photoclickhandler.photoclickcallback(position,v,holder.rvbtn_cam);
            }
        });
        holder.rvbtn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SocietyDetailsVP societyDetailsVP = new SocietyDetailsVP();
//                societyDetailsVP.num=position+8;
//                societyDetailsVP.test=holder.rvbtn_file;
//                societyDetailsVP.picDocuments();
                photoclickhandler.documentclickcallback(position,v,holder.rvbtn_file);
            }
        });

//            holder.rvEt_Value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if(holder.rvEt_title.getText().toString().equals(""))
//                    {
//                        Toast.makeText(mContext,"Set title first",Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        photoclickhandler.valuecallback(position,holder.rvEt_title.getText().toString(),holder.rvEt_Value.getText().toString());
//                    }
//
//                }
//            });
//            holder.rvEt_Value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if(actionId==EditorInfo.IME_ACTION_DONE)
//                    {
//
//                    }
//                    return false;
//                }
//            });
            holder.rvEt_Value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    photoclickhandler.valuecallback(position,holder.rvEt_title.getText().toString(),holder.rvEt_Value.getText().toString());

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
        public EditText rvEt_title,rvEt_Value;
        public Button rvbtn_file,rvbtn_cam;
        public Holder(@NonNull View itemView) {
            super(itemView);
            rvEt_title=itemView.findViewById(R.id.rvEt_title);
            rvEt_Value=itemView.findViewById(R.id.rvEt_Value);
            rvbtn_file=itemView.findViewById(R.id.rvbtn_file);
            rvbtn_cam=itemView.findViewById(R.id.rvbtn_cam);
        }
    }
}
