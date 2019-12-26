package com.icspl.createsoc.Adaptor;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.createsoc.DbConstant.DbConstant;
import com.icspl.createsoc.DbHelper.DbHelper;
import com.icspl.createsoc.Model.OwnerVPModel;
import com.icspl.createsoc.Model.OwnerVehicleModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.kartik.myapplication.utils.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerVehicleAdapter extends RecyclerView.Adapter<OwnerVehicleAdapter.Holder> {
    public Context mContext;
    public List<OwnerVehicleModel> array;
    public SharedPreferences pref;
    SharedPreferences.Editor edit;
    DbHelper dbHelper;
    SQLiteDatabase mDatabase;

    public OwnerVehicleAdapter(Context mContext, List<OwnerVehicleModel> array) {
        this.mContext = mContext;
        this.array = array;
    }

    @NonNull
    @Override
    public OwnerVehicleAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.raw_vehicle_name,parent,false);
        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OwnerVehicleAdapter.Holder holder, final int position) {
        pref=mContext.getSharedPreferences("MyPref",0);
        edit=pref.edit();
        final Calendar mycalender =  Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String Myformat ="dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Myformat, Locale.US);
                //holder.tv_coownerdob.setText(simpleDateFormat.format(mycalender.getTime()));
            }
        };
     /*   holder.tv_coownerdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext,dateSetListener,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/
        holder.savedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.et_twowheelarname.getText().toString().equals(""))
                {
                    Toast.makeText(mContext,"please enter two wheelar name",Toast.LENGTH_LONG).show();
                    return;
                }

                else
                {
                    Toast.makeText(mContext,"You save ",Toast.LENGTH_LONG).show();
                    //  String flat= spin_ownerflat.getSelectedItem().toString();

                    /*final Integer flatid=pref.getInt("flatid",0) ;
                    if(flatid.equals(0))
                    {
                        Toast.makeText(mContext,"please select flatid",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Common common= new Common();
                    common.getAPI().addownerdetails(flatid,holder.et_coownername.getText().toString(),holder.tv_coownerdob.getText().toString(),holder.et_coowneremail.getText().toString(),holder.et_coownermobile.getText().toString(),"",holder.et_coowneradd.getText().toString(),holder.et_coowneraadharcard.getText().toString(),
                            holder.et_coownerpancard.getText().toString(),holder.et_coownerrelation.getText().toString(),"co-owner",
                            "","","","","","")
                            .enqueue(new Callback<List<ServerResponseModel>>() {
                                @Override
                                public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                                    if(response.isSuccessful()&&response.body().get(0).getServerResponse().equals(1))
                                    {
                                        Toast.makeText(mContext,"CO-OWNER details send successfully",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(mContext,"Unable to send detals",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {

                                    Toast.makeText(mContext,"Network error Your data stored in device please sync",Toast.LENGTH_LONG).show();
                                }
                            });*/
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView tv_coownername,tv_coownerpancard,tv_coowneraadharcard,tv_coownerdob,tv_coowneraad,
                tv_coownermobile,tv_coowneremail,tv_coownerrelation;
        public EditText et_twowheelarname,et_coownerpancard,et_coowneraadharcard,et_coowneradd,et_coownermobile,
                et_coowneremail,et_coownerrelation;
        public Button savedetails;
        public Context context;

        public Holder(@NonNull View itemView) {
            super(itemView);
            et_twowheelarname=itemView.findViewById(R.id.et_twowheelarname);
            savedetails=itemView.findViewById(R.id.btn_Savedetails);

            dbHelper = new DbHelper(mContext);
            mDatabase = dbHelper.getWritableDatabase();
        }
    }
}
