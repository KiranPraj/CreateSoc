package com.icspl.createsoc.VIewpagerEditsoc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.OwnerVPAdaptor;
import com.icspl.createsoc.Adaptor.OwnerVehicleAdapter;
import com.icspl.createsoc.BuildConfig;
import com.icspl.createsoc.DbConstant.DbConstant;
import com.icspl.createsoc.DbHelper.DbHelper;
import com.icspl.createsoc.Model.BlockVPModel;
import com.icspl.createsoc.Model.GetFlatIdModel;
import com.icspl.createsoc.Model.GetWingIdModel;
import com.icspl.createsoc.Model.GetblockidModel;
import com.icspl.createsoc.Model.OwnerVPModel;
import com.icspl.createsoc.Model.OwnerVehicleModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.createsoc.utils.FileManager;
import com.icspl.kartik.myapplication.utils.Common;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static java.sql.DriverManager.println;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OwnerOfFlatVP.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OwnerOfFlatVP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerOfFlatVP extends Fragment {
    View v;
    public List<OwnerVPModel> list;
    public List<OwnerVehicleModel> list_vehicle;
    private Uri imageToUploadUri;
    private int IMAGE_REQUEST_CODE = 2103;
    private int DOCCUMENT_REQUEST_CODE = 2102;
    File imageBtnFile;
    public Context mContext;
    private ArrayList blockid;
    private ArrayList wingid;
    private ArrayList flatid;
    private List<GetblockidModel> blockmodel;
    private List<GetWingIdModel> wingmodel;
    private List<GetFlatIdModel> flatmodel;
    public OwnerVPAdaptor ownerVPAdaptor;
    public OwnerVehicleAdapter ownerVehicleAdapter;
    public RecyclerView rv_coowner,rv_two_whlr,rv_four_whlr;
    public LinearLayout noofcoowner;
    public Spinner coownerspin, spin_ownerblock, spin_ownerwinig, spin_ownerflat;
    public EditText et_entercoownerno, et_ownername, et_ownerpancard, et_owneraadharcard,
            et_owneraad, et_ownermobile, et_owneremail,
            et_stampduty, et_registration, et_agreement, et_poa, et_lone, et_sharecertificate,
            et_electric1, et_electric2, et_watermeter1,
            et_watermeter2, et_relativename, et_relativenumber, et_relativelandline, et_relativeemail,
            et_voting, et_meeting,et_landline;
    public TextView tv_ownerdob, tv_spinblock, tv_spinwing, tv_spinflat, tv_ownername,
            tv_ownerpancard, tv_owneraadharcard,
            tv_owneraad, tv_ownermobile, tv_owneremail, tv_stampduty, tv_registration, tv_agreement, tv_poa, tv_lone,
            tv_sharecertificate, tv_electric1, tv_electric2, tv_watermeter1,
            tv_watermeter2, tv_relativename, tv_relativenumber,
            tv_relativelandline, tv_relativeemail, tv_istherecoowner, tv_wingenter, tv_voting, tv_meeting;

    public Button btn_stampdutydoc, btn_stampdutycam, btn_registrationdoc, btn_registrationcam, btn_agreementdoc,
            btn_agreementcam,
            btn_poadoc, btn_poacam, btn_lonedoc, btn_lonecam, btn_sharecertificatecreate, btn_sharecertificatedownlode,
            btn_electric1doc, btn_electric1cam, btn_electric2doc, btn_electric2cam, btn_watermeter1doc,
            btn_Saveownerdetails,btn_savedocuments, btn_watermeter1cam, btn_watermeter2doc, btn_watermeter2cam;
    final Calendar mycalender =  Calendar.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Button test;
    public Integer num;
    private HashMap<Integer,String> document =new HashMap<Integer, String>();
    private HashMap<Integer,String> camera =new HashMap<>();
    HashMap<Integer,String> desc =new HashMap<>();
    HashMap<Integer,String> title =new HashMap<>();
    private OnFragmentInteractionListener mListener;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    DbHelper dbHelper;
    SQLiteDatabase mDatabase;
    EditText enter_no_four_wheelar,enter_no_two_wheelar;


    public OwnerOfFlatVP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerOfFlatVP.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerOfFlatVP newInstance(String param1, String param2) {
        OwnerOfFlatVP fragment = new OwnerOfFlatVP();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_owner_of_flat_v, container, false);
        list=new ArrayList<>();
        list_vehicle=new ArrayList<>();
        tv_ownerdob=v.findViewById(R.id.tv_ownerdob);
        rv_coowner=v.findViewById(R.id.rv_coowner);
        rv_two_whlr=v.findViewById(R.id.rv_two_whlr);
        rv_four_whlr=v.findViewById(R.id.rv_four_whlr);
        //All Spinner find

        coownerspin = v.findViewById(R.id.coownerspin);
        spin_ownerblock = v.findViewById(R.id.spin_ownerblock);
        spin_ownerwinig = v.findViewById(R.id.spin_ownerwinig);
        spin_ownerflat = v.findViewById(R.id.spin_ownerflat);

        //All Edittext findview
        enter_no_two_wheelar=v.findViewById(R.id.et_two_wheelar);
        enter_no_four_wheelar=v.findViewById(R.id.et_four_wheelar);
        et_entercoownerno = v.findViewById(R.id.et_entercoownerno);
        noofcoowner = v.findViewById(R.id.noofcoowner);
        et_ownername = v.findViewById(R.id.et_ownername);
        et_ownerpancard = v.findViewById(R.id.et_ownerpancard);
        et_owneraadharcard = v.findViewById(R.id.et_owneraadharcard);
        et_owneraad = v.findViewById(R.id.et_owneraad);
        et_ownermobile = v.findViewById(R.id.et_ownermobile);
        et_owneremail = v.findViewById(R.id.et_owneremail);
        et_landline=v.findViewById(R.id.et_ownerlandline);
        et_stampduty = v.findViewById(R.id.et_stampduty);
        et_registration = v.findViewById(R.id.et_registration);
        et_agreement = v.findViewById(R.id.et_agreement);
        et_poa = v.findViewById(R.id.et_poa);
        et_lone = v.findViewById(R.id.et_lone);
        et_sharecertificate = v.findViewById(R.id.et_sharecertificate);
        et_electric1 = v.findViewById(R.id.et_electric1);
        et_electric2 = v.findViewById(R.id.et_electric2);
        et_watermeter1 = v.findViewById(R.id.et_watermeter1);
        et_watermeter2 = v.findViewById(R.id.et_watermeter2);
        et_relativename = v.findViewById(R.id.et_relativename);
        et_relativenumber = v.findViewById(R.id.et_relativenumber);
        et_relativelandline = v.findViewById(R.id.et_relativelandline);
        et_relativeemail = v.findViewById(R.id.et_relativeemail);
        et_voting = v.findViewById(R.id.et_voting);
        et_meeting = v.findViewById(R.id.et_meeting);


        //All textView find
        tv_ownerdob = v.findViewById(R.id.tv_ownerdob);
        tv_spinblock = v.findViewById(R.id.tv_spinblock);
        tv_spinwing = v.findViewById(R.id.tv_spinwing);
        tv_spinflat = v.findViewById(R.id.tv_spinflat);
        tv_ownername = v.findViewById(R.id.tv_ownername);
        tv_ownerpancard = v.findViewById(R.id.tv_ownerpancard);
        tv_owneraadharcard = v.findViewById(R.id.tv_owneraadharcard);
        tv_owneraad = v.findViewById(R.id.tv_owneraad);
        tv_ownermobile = v.findViewById(R.id.tv_ownermobile);
        tv_owneremail = v.findViewById(R.id.tv_owneremail);
        tv_stampduty = v.findViewById(R.id.tv_stampduty);
        tv_registration = v.findViewById(R.id.tv_registration);
        tv_agreement = v.findViewById(R.id.tv_agreement);
        tv_poa = v.findViewById(R.id.tv_poa);
        tv_lone = v.findViewById(R.id.tv_lone);
        tv_sharecertificate = v.findViewById(R.id.tv_sharecertificate);
        tv_electric1 = v.findViewById(R.id.tv_electric1);
        tv_electric2 = v.findViewById(R.id.tv_electric2);
        tv_watermeter1 = v.findViewById(R.id.tv_watermeter1);
        tv_watermeter2 = v.findViewById(R.id.tv_watermeter2);
        tv_relativename = v.findViewById(R.id.tv_relativename);
        tv_relativenumber = v.findViewById(R.id.tv_relativenumber);
        tv_relativelandline = v.findViewById(R.id.tv_relativelandline);
        tv_relativeemail = v.findViewById(R.id.tv_relativeemail);
        tv_istherecoowner = v.findViewById(R.id.tv_istherecoowner);
        tv_wingenter = v.findViewById(R.id.tv_wingenter);
        tv_voting = v.findViewById(R.id.tv_voting);
        tv_meeting = v.findViewById(R.id.tv_meeting);


        //All Button findvieew

        btn_stampdutydoc = v.findViewById(R.id.btn_stampdutydoc);
        btn_stampdutycam = v.findViewById(R.id.btn_stampdutycam);
        btn_registrationdoc = v.findViewById(R.id.btn_registrationdoc);
        btn_registrationcam = v.findViewById(R.id.btn_registrationcam);
        btn_agreementdoc = v.findViewById(R.id.btn_agreementdoc);
        btn_agreementcam = v.findViewById(R.id.btn_agreementcam);
        btn_poadoc = v.findViewById(R.id.btn_poadoc);
        btn_poacam = v.findViewById(R.id.btn_poacam);
        btn_lonedoc = v.findViewById(R.id.btn_lonedoc);
        btn_lonecam = v.findViewById(R.id.btn_lonecam);
        btn_sharecertificatecreate = v.findViewById(R.id.btn_sharecertificatecreate);
        btn_sharecertificatedownlode = v.findViewById(R.id.btn_sharecertificatedownlode);
        btn_electric1doc = v.findViewById(R.id.btn_electric1doc);
        btn_electric1cam = v.findViewById(R.id.btn_electric1cam);
        btn_electric2doc = v.findViewById(R.id.btn_electric2doc);
        btn_electric2cam = v.findViewById(R.id.btn_electric2cam);
        btn_watermeter1doc = v.findViewById(R.id.btn_watermeter1doc);
        btn_Saveownerdetails = v.findViewById(R.id.btn_Saveownerdetails);
        btn_savedocuments=v.findViewById(R.id.btn_Saveownerdocuments);
        btn_watermeter1cam = v.findViewById(R.id.btn_watermeter1cam);
        btn_watermeter2doc = v.findViewById(R.id.btn_watermeter2doc);
        btn_watermeter2cam = v.findViewById(R.id.btn_watermeter2cam);
        pref= getActivity().getSharedPreferences("MyPref", 0);
        edit=pref.edit();

        dbHelper = new DbHelper(getActivity());
        mDatabase = dbHelper.getWritableDatabase();
        mDatabase=dbHelper.getReadableDatabase();



        final DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        datef();
            }
        } ;
        tv_ownerdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),dateSetListener,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //coownerspin.getOnItemSelectedListener().toString();
            coownerspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==1)
                    {
                        noofcoowner.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        et_entercoownerno.setText("");
                        list.clear();
                        noofcoowner.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    noofcoowner.setVisibility(View.GONE);
                }
            });

        et_entercoownerno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                if(charSequence.toString().equals(""))
                {
                    list.clear();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_coowner.setLayoutManager(linearLayoutManager);
                    ownerVPAdaptor = new OwnerVPAdaptor(getContext(),list);
                    rv_coowner.setAdapter(ownerVPAdaptor);
                    Toast.makeText(getContext(), "cannot be null or enter proper number", Toast.LENGTH_SHORT).show();

                }
                else{

                    int number=Integer.parseInt(et_entercoownerno.getText().toString());
                    list.clear();

                    for (int j = 0; j < number; j++) {
                        OwnerVPModel ownerVPModel = new OwnerVPModel("","","","","","","","");
                        list.add(ownerVPModel);
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_coowner.setLayoutManager(linearLayoutManager);
                    ownerVPAdaptor = new OwnerVPAdaptor(getContext(),list);
                    rv_coowner.setAdapter(ownerVPAdaptor);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        enter_no_two_wheelar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list_vehicle.clear();
                if(charSequence.toString().equals(""))
                {
                    list_vehicle.clear();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_two_whlr.setLayoutManager(linearLayoutManager);
                    ownerVehicleAdapter = new OwnerVehicleAdapter(getContext(),list_vehicle);
                    rv_two_whlr.setAdapter(ownerVehicleAdapter);
                    Toast.makeText(getContext(), "cannot be null or enter proper number", Toast.LENGTH_SHORT).show();

                }
                else{

                    int number=Integer.parseInt(enter_no_two_wheelar.getText().toString());
                    list_vehicle.clear();

                    for (int j = 0; j < number; j++) {
                        OwnerVehicleModel ownerVPModel = new OwnerVehicleModel("");
                        list_vehicle.add(ownerVPModel);
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_two_whlr.setLayoutManager(linearLayoutManager);
                    ownerVehicleAdapter = new OwnerVehicleAdapter(getContext(),list_vehicle);
                    rv_two_whlr.setAdapter(ownerVehicleAdapter);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        enter_no_four_wheelar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list_vehicle.clear();
                if(charSequence.toString().equals(""))
                {
                    list_vehicle.clear();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_four_whlr.setLayoutManager(linearLayoutManager);
                    ownerVehicleAdapter = new OwnerVehicleAdapter(getContext(),list_vehicle);
                    rv_four_whlr.setAdapter(ownerVehicleAdapter);
                    Toast.makeText(getContext(), "cannot be null or enter proper number", Toast.LENGTH_SHORT).show();

                }
                else{

                    int number=Integer.parseInt(enter_no_four_wheelar.getText().toString());
                    list_vehicle.clear();

                    for (int j = 0; j < number; j++) {
                        OwnerVehicleModel ownerVPModel = new OwnerVehicleModel("");
                        list_vehicle.add(ownerVPModel);
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_four_whlr.setLayoutManager(linearLayoutManager);
                    ownerVehicleAdapter = new OwnerVehicleAdapter(getContext(),list_vehicle);
                    rv_four_whlr.setAdapter(ownerVehicleAdapter);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



       Spinner();
        memberattachments();
        btn_Saveownerdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(et_ownername.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"please enter name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(spin_ownerblock.getSelectedItem().toString().equals("0")||spin_ownerwinig.getSelectedItem().toString().equals("0")||spin_ownerflat.getSelectedItem().toString().equals("0"))
                {
                    Toast.makeText(getActivity(),"Please select Block,Wings & flats",Toast.LENGTH_LONG).show();
                    return;
                }
                if(et_entercoownerno.getText().toString().equals(""))
                {
                    String flat= spin_ownerflat.getSelectedItem().toString();
                    final Integer flatno= Integer.valueOf(flat);
                   Common common = new Common();







                   common.getAPI().addownerdetails(flatno,et_ownername.getText().toString(),tv_ownerdob.getText().toString(),et_owneremail.getText().toString(),et_ownermobile.getText().toString()
                            ,et_landline.getText().toString(),et_owneraad.getText().toString(),et_owneraadharcard.getText().toString(),et_ownerpancard.getText().toString(),
                            "Owner","Owner",et_voting.getText().toString(),et_meeting.getText().toString(),et_relativename.getText().toString(),
                            et_relativenumber.getText().toString(),et_relativelandline.getText().toString(),et_relativeemail.getText().toString())
                            .enqueue(new Callback<List<ServerResponseModel>>() {
                                @Override
                                public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                                    if(response.isSuccessful()&&response.body().get(0).getServerResponse().equals(1))
                                    {
                                        Toast.makeText(getActivity(),"Owner dteails added successfully please wait till all documents get added",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getActivity(),"Response  Error",Toast.LENGTH_LONG).show();
                                        ContentValues values = new ContentValues();
                                        values.put(DbConstant.OwnerDetails.FID,flatno);
                                        values.put(DbConstant.OwnerDetails.ONAME,et_ownername.getText().toString());
                                        values.put(DbConstant.OwnerDetails.ODOB,tv_ownerdob.getText().toString());
                                        values.put(DbConstant.OwnerDetails.OEMAIL,et_owneremail.getText().toString());
                                        values.put(DbConstant.OwnerDetails.OMOBNO,et_ownermobile.getText().toString());
                                        values.put(DbConstant.OwnerDetails.OLANDLINE,et_landline.getText().toString());
                                        values.put(DbConstant.OwnerDetails.OADD,et_owneraad.getText().toString());
                                        values.put(DbConstant.OwnerDetails.OAADHAR,et_owneraadharcard.getText().toString());
                                        values.put(DbConstant.OwnerDetails.OPAN,et_ownerpancard.getText().toString());
                                        values.put(DbConstant.OwnerDetails.ORELATION,"Owner");
                                        values.put(DbConstant.OwnerDetails.OTYPE,"Owner");
                                        values.put(DbConstant.OwnerDetails.VOTING_RIGHT,et_voting.getText().toString());
                                        values.put(DbConstant.OwnerDetails.ATTENDING_MEETING,et_meeting.getText().toString());
                                        values.put(DbConstant.OwnerDetails.RELATIVE_NAME,et_relativename.getText().toString());
                                        values.put(DbConstant.OwnerDetails.RELATIVE_NO,et_relativenumber.getText().toString());
                                        values.put(DbConstant.OwnerDetails.RELATIVE_LANDLINE,et_relativelandline.getText().toString());
                                        values.put(DbConstant.OwnerDetails.RELATIVE_EMAIL,et_relativeemail.getText().toString());
                                       int id= (int) mDatabase.insert(DbConstant.OwnerDetails.TABLE_OWNER_DETAILS,null,values);
                                 /* if (id>0)
                                  {
                                     // Toast.makeText(getActivity(),"inserted",Toast.LENGTH_LONG).show();
                                  }
                                  else
                                  {
                                      Toast.makeText(getActivity(),"not inserted",Toast.LENGTH_LONG).show();
                                  }*/
                                    }

                                }

                                @Override
                                public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                                    ContentValues values = new ContentValues();
                                    values.put(DbConstant.OwnerDetails.FID,flatno);
                                    values.put(DbConstant.OwnerDetails.ONAME,et_ownername.getText().toString());
                                    values.put(DbConstant.OwnerDetails.ODOB,tv_ownerdob.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OEMAIL,et_owneremail.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OMOBNO,et_ownermobile.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OLANDLINE,et_landline.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OADD,et_owneraad.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OAADHAR,et_owneraadharcard.getText().toString());
                                    values.put(DbConstant.OwnerDetails.OPAN,et_ownerpancard.getText().toString());
                                    values.put(DbConstant.OwnerDetails.ORELATION,"Owner");
                                    values.put(DbConstant.OwnerDetails.OTYPE,"Owner");
                                    values.put(DbConstant.OwnerDetails.VOTING_RIGHT,et_voting.getText().toString());
                                    values.put(DbConstant.OwnerDetails.ATTENDING_MEETING,et_meeting.getText().toString());
                                    values.put(DbConstant.OwnerDetails.RELATIVE_NAME,et_relativename.getText().toString());
                                    values.put(DbConstant.OwnerDetails.RELATIVE_NO,et_relativenumber.getText().toString());
                                    values.put(DbConstant.OwnerDetails.RELATIVE_LANDLINE,et_relativelandline.getText().toString());
                                    values.put(DbConstant.OwnerDetails.RELATIVE_EMAIL,et_relativeemail.getText().toString());
                                    mDatabase.insert(DbConstant.OwnerDetails.TABLE_OWNER_DETAILS,null,values);
                                    Toast.makeText(getActivity(),"No internet",Toast.LENGTH_LONG).show();
                                }
                            });
                }

            }
        });
        title.put(1,"Stampduty");
        title.put(2,"Registration");
        title.put(3,"Agreement");
        title.put(4,"POA");
        title.put(5,"Loan");
        title.put(6,"Electric meter 1");
        title.put(7,"Electric meter 2");
        title.put(8,"Water meter 1");
        title.put(9,"Water meter 2");
        btn_savedocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin_ownerblock.getSelectedItem().toString().equals("0")||spin_ownerwinig.getSelectedItem().toString().equals("0")||spin_ownerflat.getSelectedItem().toString().equals("0"))
                {
                    Toast.makeText(getActivity(),"Please select Block,Wings & flats",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!et_stampduty.getText().toString().equals(""))
                {
                    desc.put(1,et_stampduty.getText().toString());
                }
                if(!et_registration.getText().toString().equals(""))
                {
                    desc.put(2,et_registration.getText().toString());
                }
                if(!et_agreement.getText().toString().equals(""))
                {
                    desc.put(3, et_agreement.getText().toString());
                }
                if(!et_poa.getText().toString().equals(""))
                {
                    desc.put(4,et_poa.getText().toString());
                }
                if(!et_lone.getText().toString().equals(""))
                {
                    desc.put(5,et_lone.getText().toString());
                }
                if(!et_electric1.getText().toString().equals(""))
                {
                    desc.put(6,et_electric1.getText().toString());
                }
                if(!et_electric2.getText().toString().equals(""))
                {

                    desc.put(7,et_electric2.getText().toString());
                }
                if(!et_watermeter1.getText().toString().equals(""))
                {
                    desc.put(8,et_watermeter1.getText().toString());
                }
                if(!et_watermeter2.getText().toString().equals(""))
                {
                    desc.put(9,et_watermeter2.getText().toString());
                }
                for (int i = 1; i <=9 ; i++)
                {
                    String flat= spin_ownerflat.getSelectedItem().toString();
                    final Integer flatid= Integer.valueOf(flat);
                    String description="";
                    if(desc.containsKey(i))
                    {
                        description=desc.get(i);
                    }
                    MultipartBody.Part sendDocument;
                    MultipartBody.Part sendImage;
                    if(document.containsKey(i)&&camera.containsKey(i))
                    {
                        File doc= new File(document.get(i));
                        final RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),doc);
                        sendDocument=MultipartBody.Part.createFormData("ddoc",doc.getName(),requestBody);
                        File cam= new File(camera.get(i));
                        RequestBody requestBody1= RequestBody.create(MediaType.parse("multipart/form-data"),cam);
                        sendImage=MultipartBody.Part.createFormData("dimage",cam.getName(),requestBody1);
                        sendData(flatid,title.get(i),description,i,sendDocument,sendImage,document.get(i),camera.get(i));

                    }
                    else if(document.containsKey(i))
                    {
                        File doc= new File(document.get(i));
                        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),doc);
                        sendDocument=MultipartBody.Part.createFormData("ddoc",doc.getName(),requestBody);
                        RequestBody cam=RequestBody.create(MultipartBody.FORM,"");
                        sendImage=MultipartBody.Part.createFormData("dimage","",cam);
                        sendData(flatid,title.get(i),description,i,sendDocument,sendImage,document.get(i),camera.get(i));
                    }
                    else if(camera.containsKey(i))
                    {
                        RequestBody doc=RequestBody.create(MultipartBody.FORM,"");
                        sendDocument=MultipartBody.Part.createFormData("ddoc","",doc);
                        File cam= new File(camera.get(i));
                        RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/form-data"),cam);
                        sendImage=MultipartBody.Part.createFormData("dimage",cam.getName(),requestBody);
                        sendData(flatid,title.get(i),description,i,sendDocument,sendImage,document.get(i),camera.get(i));

                    }
                    else
                    {
                        RequestBody cam=RequestBody.create(MultipartBody.FORM,"");
                        sendImage=MultipartBody.Part.createFormData("dimage","",cam);
                        RequestBody doc=RequestBody.create(MultipartBody.FORM,"");
                        sendDocument=MultipartBody.Part.createFormData("ddoc","",doc);
                        sendData(flatid,title.get(i),description,i,sendDocument,sendImage,document.get(i),camera.get(i));
                    }

                }




            }
        });
        return v;
    }

    private void sqlitedatain( int flat)
    {
   Cursor cursor=mDatabase.rawQuery("select * from table_ownerdetails where fid= '"+flat+"'",null);
   if (cursor.getCount()>0)
   {
       cursor.moveToFirst();
       String oname=cursor.getString(cursor.getColumnIndex("oname"));
       et_ownername.setText(oname);
       et_ownerpancard.setText(cursor.getString(cursor.getColumnIndex("opan")));
       et_owneraadharcard.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.OAADHAR)));
       tv_ownerdob.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.ODOB)));
       et_owneraad.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.OADD)));
       et_ownermobile.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.OMOBNO)));
       et_landline.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.OLANDLINE)));

       et_owneremail.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.OEMAIL)));
       et_voting.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.VOTING_RIGHT)));
       et_meeting.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.ATTENDING_MEETING)));
       et_relativename.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.RELATIVE_NAME)));
       et_relativenumber.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.RELATIVE_NO)));
       et_relativelandline.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.RELATIVE_LANDLINE)));
       et_relativeemail.setText(cursor.getString(cursor.getColumnIndex(DbConstant.OwnerDetails.RELATIVE_EMAIL)));
   }
    }

    private void sendData(final Integer flatid, final String title, final String description, final int i, MultipartBody.Part sendDocument, MultipartBody.Part sendImage, final String docpath, final String campath) {
        Common common= new Common();
        common.getAPI().addownerdocuments(flatid,title,description,i,sendDocument,sendImage)
                .enqueue(new Callback<List<ServerResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                        if(response.isSuccessful()&&response.body().get(0).getServerResponse().equals(1))
                        {
                            Toast.makeText(getActivity(),"document"+title+" added successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Failed to upload documents"+title,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                        ContentValues values= new ContentValues();
                        values.put(DbConstant.OwnerDocuments.TITLE,title);
                        values.put(DbConstant.OwnerDocuments.DESCRIPTION,description);
                        values.put(DbConstant.OwnerDocuments.DOCUMENT,docpath);
                        values.put(DbConstant.OwnerDocuments.PHOTO,campath);
                        values.put(DbConstant.OwnerDocuments.FLAT_ID,flatid);
                        values.put(DbConstant.OwnerDocuments.NUMBER,i);
                        mDatabase.insert(DbConstant.OwnerDocuments.TABLE_SOCIETY_DOCUMENT,null,values);
                        Toast.makeText(getActivity(),"Network error",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void memberattachments() {
        btn_stampdutydoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test=btn_stampdutydoc;
                num=1;
                picDocuments();
            }
        });
        btn_stampdutycam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                test=btn_stampdutycam;
                num=1;
                pickimage();

            }
        });
        btn_registrationdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test=btn_registrationdoc;
                num=2;
                picDocuments();
            }
        });
        btn_registrationcam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                test=btn_registrationcam;
                num=2;
                pickimage();
            }
        });
        btn_agreementdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test=btn_agreementdoc;
                num=3;
                picDocuments();
            }
        });
        btn_agreementcam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                test=btn_agreementcam;
                num=3;
                pickimage();
            }
        });
        btn_poadoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test=btn_poadoc;
                num=4;
                picDocuments();
            }
        });
        btn_poacam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                test=btn_poacam;
                num=4;
                pickimage();
            }
        });
        btn_lonecam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                num=5;
                test=btn_lonecam;
                pickimage();
            }
        });
        btn_lonedoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=5;
                test=btn_lonedoc;
                picDocuments();
            }
        });
        btn_electric1cam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                num=6;
                test=btn_electric1cam;
                pickimage();
            }
        });
        btn_electric1doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=6;
                test=btn_electric1doc;
                picDocuments();
            }
        });
        btn_electric2cam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                num=7;
                test=btn_electric2cam;
                pickimage();
            }
        });
        btn_electric2doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=7;
                test=btn_electric2doc;
                picDocuments();
            }
        });
        btn_watermeter1cam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                num=8;
                test=btn_watermeter1cam;
                pickimage();
            }
        });
        btn_watermeter1doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=8;
                test=btn_watermeter1doc;
                picDocuments();
            }
        });
        btn_watermeter2cam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                num=9;
                test=btn_watermeter2cam;
                pickimage();
            }
        });
        btn_watermeter2doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=9;
                test=btn_watermeter2doc;
                picDocuments();
            }
        });

    }
    public void picDocuments() {

        try {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            //                        Uri uri = Uri.parse(Environment.getRootDirectory().getAbsolutePath());
            //                        fileIntent.setData(uri);
            fileIntent.setType("application/pdf")  ;
            String[] extraMimeTypes =new String[]
                    {
                            "audio/*",
                            "video/*",
                            "application/*",
                            "application/pdf",
                            "application/msword",
                            "application/vnd.ms-powerpoint",
                            "application/vnd.ms-excel",
                            "application/zip",
                            "audio/x-wav|text/plain"
                    };
            fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
            startActivityForResult(fileIntent, DOCCUMENT_REQUEST_CODE);
        } catch ( Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void pickimage() {
        String dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/ICS SMS/Images/";
        File newdir = new File(dir);
        newdir.mkdirs();

        String file = dir + "SMS_" + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";

        imageBtnFile = new File(file);
        try {
            imageBtnFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        imageToUploadUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", imageBtnFile);
//        File impfile=new File(context.getFilesDir(),"");
//        Uri imageToUploadUri=getUriForFile()

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            cameraIntent.setClipData(ClipData.newRawUri("", imageToUploadUri));

            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(cameraIntent, IMAGE_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST_CODE)
        {
            imageActivityResult();
        }
        else if(requestCode==DOCCUMENT_REQUEST_CODE)
        {
            documentResultHandler(resultCode, data, test);
        }

    }
    public void documentResultHandler(int resultCode, Intent data,Button fileButton) {
        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String picturePath = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    picturePath = FileManager.getPath(getContext(),selectedImage);
                }
                //Log.i(TAG, "documentResultHandler: " + picturePath);
                try {
                    File file = new File(picturePath);
                    if (file.exists()) {
                        if (file.length() / 1024 >= 5120) { //15 mb
                            Toast.makeText(mContext, "File Size limit is upto 5MB", Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }
                    }
                } catch ( java.lang.Exception e) {
                    e.printStackTrace();
                }

                println("picturePath +" + picturePath);  //path of sdcard
                if(picturePath != null)
                {

                    document.put(num,picturePath);
                    test.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Toast.makeText(getActivity(), "fle attached", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "not attached", Toast.LENGTH_SHORT).show();
                }

            }

        } else
            Toast.makeText(getActivity(), getString(R.string.error_file_executong), Toast.LENGTH_LONG).show();


    }

    @SuppressLint("StaticFieldLeak")
    public void imageActivityResult() {
        try {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    //Activity activity= new Activity();
                    String path = compressImage(imageBtnFile.toString(),getActivity());
                    Log.i(TAG, "doInBackground: path: " + path);
                    return path;
                }


                @Override
                protected void onPostExecute(String path) {
                    super.onPostExecute(path);
                    try {
                        if (path != null) {
                            camera.put(num,path);
                            test.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            Toast.makeText(getActivity(), "fle attached", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "not attached", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to Attach File", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(mContext, "Failed to Attach File", Toast.LENGTH_SHORT).show();
        }


    }

    public static String compressImage(String imageUri, Activity activity) {
        String filename = "";
        try {
            String filePath = getRealPathFromURI(imageUri,activity);
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas;
            if (scaledBitmap != null) {
                canvas = new Canvas(scaledBitmap);
                canvas.setMatrix(scaleMatrix);
                canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
            }


            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);

                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);

                } else if (orientation == 3) {
                    matrix.postRotate(180);

                } else if (orientation == 8) {
                    matrix.postRotate(270);

                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out;
            filename = getFilename();
            try {
                out = new FileOutputStream(filename);
                if (scaledBitmap != null) {
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filename;
    }

    private static String getFilename() {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), "ICS SMS/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + "SMS_" + UUID.randomUUID().toString() + "_IMG_" + System.currentTimeMillis() + ".jpg";

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private static String getRealPathFromURI(String contentURI,Activity activity) {
        Uri contentUri = Uri.parse(contentURI);

        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    private void Spinner() {

        blockid= new ArrayList();
        wingid= new ArrayList();
        flatid=new ArrayList();
        blockid.add("Select");
        edit.remove("flatid");
        edit.apply();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Common.Companion.getBASE_URL()).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getblock(pref.getString("S_id","")).enqueue(new Callback<List<GetblockidModel>>() {
            @Override
            public void onResponse(Call<List<GetblockidModel>> call, Response<List<GetblockidModel>> response) {
                if(response.body().size()>0)
                {
                    blockmodel=response.body();
                    for (int i = 0; i <response.body().size() ; i++) {
                        if(blockmodel.get(i).getBlockName()!=null)
                        {
                            blockid.add(blockmodel.get(i).getBlockName());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            blockid
                    );
                    spin_ownerblock.setAdapter(adapter);
                }
                else
                {
                    blockid.add("Select");
                    Toast.makeText(getContext(),"no blocks available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetblockidModel>> call, Throwable t) {

            }
        });

        spin_ownerblock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                {
                    wingid.clear();
                    flatid.clear();
                    wingid.add("Select");
                    String block=blockmodel.get(spin_ownerblock.getSelectedItemPosition()-1).getBlockId();
                    Common common= new Common();
                    common.getAPI().getwings(pref.getString("S_id",""),block).enqueue(new Callback<List<GetWingIdModel>>() {
                        @Override
                        public void onResponse(Call<List<GetWingIdModel>> call, Response<List<GetWingIdModel>> response) {
                            if(response.body().size()>0)
                            {
                                wingmodel=response.body();
                                for (int j = 0; j < response.body().size() ; j++) {
                                    if(response.body().get(j).getWingName()!=null)
                                    {
                                        wingid.add(response.body().get(j).getWingName());
                                    }
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        getActivity(),
                                        android.R.layout.simple_spinner_item,
                                        wingid
                                );
                                spin_ownerwinig.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            else
                            {
                                wingid.add("Select");
                                Toast.makeText(getActivity(),"No Wings Available",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<GetWingIdModel>> call, Throwable t) {
                            Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_LONG).show();
                        }
                    });


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(),"Please select",Toast.LENGTH_LONG).show();
            }
        });
        spin_ownerwinig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    flatid.clear();
                    flatid.add("Select");
                    String block=blockmodel.get(spin_ownerblock.getSelectedItemPosition()-1).getBlockId();
                    String wing= wingmodel.get(spin_ownerwinig.getSelectedItemPosition()-1).getWingId();
                    Common common= new Common();
                    common.getAPI().getflats(pref.getString("S_id",""),block,wing).enqueue(new Callback<List<GetFlatIdModel>>() {
                        @Override
                        public void onResponse(Call<List<GetFlatIdModel>> call, Response<List<GetFlatIdModel>> response) {
                            assert response.body() != null;
                            if (response.body().size() > 0) {
                                flatmodel = response.body();
                                for (int j = 0; j < response.body().size(); j++) {
                                    if(response.body().get(j).getFlatNo()!=null)
                                    {
                                        flatid.add(response.body().get(j).getFlatNo());
                                    }
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        getActivity(),
                                        android.R.layout.simple_spinner_item,
                                        flatid
                                );
                                spin_ownerflat.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                flatid.add("Select");
                                Toast.makeText(getActivity(), "No Flats Available", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<GetFlatIdModel>> call, Throwable t) {
                            Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getActivity(), "Please Select Wing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    spin_ownerflat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           // String flatid=flatmodel.get(spin_ownerflat.getSelectedItemPosition()).getFlatId();
            if(position>0)
            {

                String flatid=flatmodel.get(spin_ownerflat.getSelectedItemPosition()-1).getFlatNo();
                int flatnoo= Integer.parseInt(flatid);
                sqlitedatain(flatnoo);
                edit.putInt("flatid",flatnoo);
                edit.apply();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    }

    private void datef() {
        String Myformat ="dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Myformat, Locale.US);
        tv_ownerdob.setText(simpleDateFormat.format(mycalender.getTime()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
