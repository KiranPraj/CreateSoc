package com.icspl.createsoc.Adaptor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.icspl.createsoc.Model.CreateMeetingModel;
import com.icspl.createsoc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class CreateMeetingAdaptor extends RecyclerView.Adapter<CreateMeetingAdaptor.Holder> {
    public Context mContext;
    public List<CreateMeetingModel> array;
    private Meetinghandler meetinghandler;
    final Calendar mycalender =  Calendar.getInstance();
    public CreateMeetingAdaptor(Context mContext, List<CreateMeetingModel> array,Meetinghandler meetinghandler) {
        this.mContext = mContext;
        this.array = array;
        this.meetinghandler= meetinghandler;
    }
    public interface Meetinghandler{
            void title(Integer pos,String title);
            void date(Integer pos,String date);
    }

    @NonNull
    @Override
    public CreateMeetingAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_adaptorcreatemeeting,parent,false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CreateMeetingAdaptor.Holder holder, final int position) {
        holder.et_discription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                meetinghandler.title(position,holder.et_discription.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.tv_meetingdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                meetinghandler.date(position,holder.tv_meetingdate.getText().toString());

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
        public EditText et_discription;
        public TextView tv_meetingdate;

        public Holder(@NonNull View itemView) {
            super(itemView);

            et_discription=itemView.findViewById(R.id.et_discription);
            tv_meetingdate=itemView.findViewById(R.id.tv_meetingdate);
            final long today = System.currentTimeMillis() - 1000;

         final DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                   /*
                    long sss=Calendar.getInstance().getTimeInMillis();
                    Log.i("TAG","currentdate1" +ss +"currentdate2"+ sss);
                     datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());
*/
                    //long ss=mycalender.getTimeInMillis();
                    mycalender.set(Calendar.YEAR,year);
                    mycalender.set(Calendar.MONTH,month);
                    mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);


                    datef();
                }

            } ;

            tv_meetingdate.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {



                    new DatePickerDialog(mContext,dateSetListener,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();


                }

            });


        }

        private void datef() {
            String Myformat ="dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Myformat, Locale.US);
            tv_meetingdate.setText(simpleDateFormat.format(mycalender.getTime()));
        }
    }
}
