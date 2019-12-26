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

import com.icspl.createsoc.DbConstant.DbConstant;
import com.icspl.createsoc.DbHelper.DbHelper;
import com.icspl.createsoc.Model.OwnerVPModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerVPAdaptor extends RecyclerView.Adapter<OwnerVPAdaptor.Holder> {
    public Context mContext;
    public List<OwnerVPModel> array;
    public SharedPreferences pref;
    SharedPreferences.Editor edit;
    DbHelper dbHelper;
    SQLiteDatabase mDatabase;

    public OwnerVPAdaptor(Context mContext, List<OwnerVPModel> array) {
        this.mContext = mContext;
        this.array = array;
    }

    @NonNull
    @Override
    public OwnerVPAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_coowner,parent,false);
        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OwnerVPAdaptor.Holder holder, final int position) {
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
                holder.tv_coownerdob.setText(simpleDateFormat.format(mycalender.getTime()));
            }
        };
        holder.tv_coownerdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext,dateSetListener,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        holder.savecoownerdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.et_coownername.getText().toString().equals(""))
                {
                    Toast.makeText(mContext,"please enter co-owner name",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                 //  String flat= spin_ownerflat.getSelectedItem().toString();

                  //  final Integer flatid=pref.getInt("flatid",0) ;
                    final Integer flatid=pref.getInt("flatid",0) ;

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
                                        Toast.makeText(mContext,"Unable to send details",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                                    ContentValues values= new ContentValues();
                                    values.put(DbConstant.OwnerDetails.FID,flatid);
                                    values.put(DbConstant.OwnerDetails.ONAME,holder.et_coownername.getText().toString());
                                    values.put(DbConstant.OwnerDetails.ODOB,holder.tv_coownerdob.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OEMAIL,holder.et_coowneremail.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OMOBNO,holder.et_coownermobile.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OLANDLINE,"");
                                    values.put(DbConstant.OwnerDetails.OADD,holder.et_coowneradd.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OAADHAR,holder.et_coowneraadharcard.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OPAN, holder.et_coownerpancard.getText().toString());
                                    values.put(DbConstant.OwnerDetails.ORELATION,holder.et_coownerrelation.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OTYPE,"co-owner");
                                    values.put(DbConstant.OwnerDetails.VOTING_RIGHT,"");
                                    values.put(DbConstant.OwnerDetails.ATTENDING_MEETING,"");
                                    values.put(DbConstant.OwnerDetails.RELATIVE_NAME,"");
                                    values.put(DbConstant.OwnerDetails.RELATIVE_NO,"");
                                    values.put(DbConstant.OwnerDetails.RELATIVE_LANDLINE,"");
                                    values.put(DbConstant.OwnerDetails.RELATIVE_EMAIL,"");
                                    mDatabase.insert(DbConstant.OwnerDetails.TABLE_OWNER_DETAILS,null,values);
                                    Toast.makeText(mContext,"Network error Your data stored in device please sync",Toast.LENGTH_LONG).show();
                                }
                            });
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
        public EditText et_coownername,et_coownerpancard,et_coowneraadharcard,et_coowneradd,et_coownermobile,
                et_coowneremail,et_coownerrelation;
        public Button savecoownerdetails;
        public Context context;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_coownername=itemView.findViewById(R.id.tv_coownername);
            tv_coownerpancard=itemView.findViewById(R.id.tv_coownerpancard);
            tv_coowneraadharcard=itemView.findViewById(R.id.tv_coowneraadharcard);
            tv_coownerdob=itemView.findViewById(R.id.tv_coownerdob);
            tv_coowneraad=itemView.findViewById(R.id.tv_coowneraad);
            tv_coownermobile=itemView.findViewById(R.id.tv_coownermobile);
            tv_coowneremail=itemView.findViewById(R.id.tv_coowneremail);
            tv_coownerrelation=itemView.findViewById(R.id.tv_coownerrelation);
            et_coownername=itemView.findViewById(R.id.et_coownername);
            et_coownerpancard=itemView.findViewById(R.id.et_coownerpancard);
            et_coowneraadharcard=itemView.findViewById(R.id.et_coowneraadharcard);
            et_coowneradd=itemView.findViewById(R.id.et_coowneradd);
            et_coownermobile=itemView.findViewById(R.id.et_coownermobile);
            et_coowneremail=itemView.findViewById(R.id.et_coowneremail);
            et_coownerrelation=itemView.findViewById(R.id.et_coownerrelation);
            savecoownerdetails=itemView.findViewById(R.id.btn_Savecoownerdetails);
            dbHelper = new DbHelper(mContext);
            mDatabase = dbHelper.getWritableDatabase();
        }
    }
}
