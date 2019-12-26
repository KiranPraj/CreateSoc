package com.icspl.createsoc.VIewpagerEditsoc;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.FlatVPAdaptor;
import com.icspl.createsoc.DbConstant.DbConstant;
import com.icspl.createsoc.DbHelper.DbHelper;
import com.icspl.createsoc.Model.BlockVPModel;
import com.icspl.createsoc.Model.FlatVPModel;
import com.icspl.createsoc.Model.GetFlatIdModel;
import com.icspl.createsoc.Model.GetWingIdModel;
import com.icspl.createsoc.Model.GetblockidModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.Model.WingVPModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FlatVP.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FlatVP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlatVP extends Fragment implements FlatVPAdaptor.Flathandler {
    View v;
    public Spinner spin_block,spin_winig;
    public EditText et_enterflat;
    public RecyclerView rv_flat;
    public Button btn_Saveflatdetailsl;
    public List<FlatVPModel> list;
    public FlatVPAdaptor  flatVPAdaptor;
    private HashMap<Integer,String> flatname= new HashMap<Integer, String>();
    private HashMap<Integer,String> flatnumber= new HashMap<Integer, String>();
    private HashMap<Integer,String> flatnoofoccupants= new HashMap<Integer, String>();
    private HashMap<Integer,String> flatbuilarea= new HashMap<Integer, String>();
    private HashMap<Integer,String> flatcarpetarea= new HashMap<Integer, String>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private List<GetblockidModel> blockmodel= new ArrayList<>();
    private List<GetWingIdModel> wingmodel= new ArrayList<>();
    private List<GetFlatIdModel> flatmodel= new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList blockid;
    private ArrayList wingid;

    DbHelper dbHelper;
    SQLiteDatabase mDatabase;

    private OnFragmentInteractionListener mListener;

    public FlatVP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlatVP.
     */
    // TODO: Rename and change types and number of parameters
    public static FlatVP newInstance(String param1, String param2) {
        FlatVP fragment = new FlatVP();
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
        list= new ArrayList<>();
        v= inflater.inflate(R.layout.fragment_flat_v, container, false);
        spin_block=v.findViewById(R.id.spin_block);
        spin_winig=v.findViewById(R.id.spin_winig);
        et_enterflat=v.findViewById(R.id.et_enterflat);
        rv_flat=v.findViewById(R.id.rv_flat);
        btn_Saveflatdetailsl=v.findViewById(R.id.btn_Saveflatdetails);
          blockid= new ArrayList<>();
          wingid= new ArrayList<>();
        dbHelper = new DbHelper(getActivity());
        mDatabase = dbHelper.getWritableDatabase();
        preferences= getActivity().getSharedPreferences("MyPref", 0);
        editor=preferences.edit();


        et_enterflat.addTextChangedListener(new TextWatcher() {
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
                    rv_flat.setLayoutManager(linearLayoutManager);
                    flatVPAdaptor = new FlatVPAdaptor(getContext(),list,FlatVP.this);
                    rv_flat.setAdapter(flatVPAdaptor);
                    Toast.makeText(getContext(), "cannot be null", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(et_enterflat.getText().toString().matches("[0-9]+"))
                    {
                        int number=Integer.parseInt(et_enterflat.getText().toString());
                        list.clear();

                        for (int j = 0; j < number; j++) {
                            FlatVPModel flatVPModel = new FlatVPModel("","");
                            list.add(flatVPModel);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        rv_flat.setLayoutManager(linearLayoutManager);
                        flatVPAdaptor = new FlatVPAdaptor(getContext(),list,FlatVP.this);
                        rv_flat.setAdapter(flatVPAdaptor);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"please enter valid number",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //set blocks
        Common common= new Common();
        common.getAPI().getblock(preferences.getString("S_id","")).enqueue(new Callback<List<GetblockidModel>>() {
            @Override
            public void onResponse(Call<List<GetblockidModel>> call, Response<List<GetblockidModel>> response) {
                if(response.body().size()>0)
                {
                    blockid.clear();
                    blockid.add("Select");
                    blockmodel=response.body();
                    for (int i = 0; i <response.body().size() ; i++) {
                        blockid.add(response.body().get(i).getBlockName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, blockid);
                    spin_block.setAdapter(adapter);
                }
                else
                {
                    blockid.add("Select");
                    Toast.makeText(getContext(),"no blocks available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetblockidModel>> call, Throwable t) {
                Toast.makeText(getContext(),"Network error",Toast.LENGTH_LONG).show();
            }
        });


        spin_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                {
                    wingid.clear();
                    wingid.add("Select");
                    int pos=spin_block.getSelectedItemPosition()-1;
                    String bid=blockmodel.get(pos).getBlockId();
                    Common common= new Common();
                    common.getAPI().getwings(preferences.getString("S_id",""),bid).enqueue(new Callback<List<GetWingIdModel>>() {
                        @Override
                        public void onResponse(Call<List<GetWingIdModel>> call, Response<List<GetWingIdModel>> response) {
                            if(response.body().size()>0)
                            {
                                wingmodel=response.body();
                                wingid.clear();
                                wingid.add("Select");
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
                                spin_winig.setAdapter(adapter);
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
       /* spin_winig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sqlitedataofflatdetails(spin_block.getSelectedItem().toString(),spin_winig.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/





        btn_Saveflatdetailsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(et_enterflat.getText().toString().equals(""))
                {
                   Toast.makeText(getContext(),"please enter number of flats",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(spin_block.getSelectedItem().toString().equals("Select"))
                    {
                        Toast.makeText(getActivity(),"Please select block",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(spin_winig.getSelectedItem().toString().equals("Select"))
                    {
                        Toast.makeText(getActivity(),"please select wing",Toast.LENGTH_LONG).show();
                        return;
                    }

                    int number = Integer.parseInt(et_enterflat.getText().toString());
                    if(flatname.size()!=number || flatnumber.size()!=number||flatnoofoccupants.size()!=number||flatbuilarea.size()!=number||flatcarpetarea.size()!=number)
                    {
                        Toast.makeText(getActivity(),"Please fill All values",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 1; i <=number ; i++) {
                        Common common= new Common();
                        final int finalI = i;
                        String bid= blockmodel.get(spin_block.getSelectedItemPosition()-1).getBlockId();
                        String wid=wingmodel.get(spin_winig.getSelectedItemPosition()-1).getWingId();

                        common.getAPI().addflats(preferences.getString("S_id",""),bid,wid,flatnumber.get(i),flatname.get(i),flatnoofoccupants.get(i),flatbuilarea.get(i),flatcarpetarea.get(i))
                                .enqueue(new Callback<List<ServerResponseModel>>() {
                                    @Override
                                    public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                                        if(response.body().get(0).getServerResponse()==1&&response.isSuccessful())
                                        {
                                            Toast.makeText(getActivity(),"Flat no "+flatnumber.get(finalI)+"Added succesffuly",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(), "Data not sent of flat no "+ finalI, Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                                        ContentValues values = new ContentValues();
                                        values.put(DbConstant.FlatDetails.BLOCK_ID ,spin_block.getSelectedItem().toString());
                                        values.put(DbConstant.FlatDetails.WING_ID ,spin_winig.getSelectedItem().toString());
                                        values.put(DbConstant.FlatDetails.FLAT_NUMBER ,flatnumber.get(finalI));
                                        values.put(DbConstant.FlatDetails.FLAT_NAME ,flatname.get(finalI));
                                        values.put(DbConstant.FlatDetails.NO_OF_OCCUPANTS ,flatnoofoccupants.get(finalI));
                                        values.put(DbConstant.FlatDetails.BUILD_UP_AREA ,flatbuilarea.get(finalI));
                                        values.put(DbConstant.FlatDetails.CARPET_AREA ,flatcarpetarea.get(finalI));
                                        Long id=mDatabase.insert(DbConstant.FlatDetails.TABLE_FLAT_DETAILS,null,values);
                                        if(id>0)
                                            Toast.makeText(getActivity(),"Network error Your data stored in device please sync",Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getActivity(),"Not saved",Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                    Toast.makeText(getActivity(),"flats added successfully",Toast.LENGTH_LONG).show();
                }
            }
        });
        return  v;

    }

    private void sqlitedataofflatdetails(String blockname, String wingname)
    {
        Cursor cursor=mDatabase.rawQuery("select * from flat_details where block_id='"+blockname+"' and  wing_id= '"+wingname+"'",null);
   if (cursor.getCount()>0)
   {

      for (int i=0;i<=cursor.getCount();i++)
      {
          String flatname=cursor.getString(cursor.getColumnIndex(DbConstant.FlatDetails.FLAT_NAME));
      }


   }

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

    @Override
    public void flatname(Integer pos, String name) {
        if(name.equals(""))
        {
            if(flatname.containsKey(pos+1))
            {
                flatname.remove(pos+1);
            }
        }
        else
        {
            flatname.put(pos+1,name);
        }
    }

    @Override
    public void flatnumber(Integer pos, String number) {
        if(number.equals(""))
        {
            if(flatnumber.containsKey(pos+1))
            {
                flatnumber.remove(pos+1);
            }
        }
        else
        {
            flatnumber.put(pos+1,number);
        }
    }

    @Override
    public void flatnoofoccupants(Integer pos, String noofoccupants) {
        if(noofoccupants.equals(""))
        {
            if(flatnoofoccupants.containsKey(pos+1))
            {
                flatnoofoccupants.remove(pos+1);
            }
        }
        else
        {
            flatnoofoccupants.put(pos+1,noofoccupants);
        }
    }

    @Override
    public void flatbuilduparea(Integer pos, String builduparea) {
        if(builduparea.equals(""))
        {
            if(flatbuilarea.containsKey(pos+1))
            {
                flatbuilarea.remove(pos+1);
            }
        }
        else
        {
            flatbuilarea.put(pos+1,builduparea);
        }
    }

    @Override
    public void flatcarpetarea(Integer pos, String carpetarea) {
        if(carpetarea.equals(""))
        {
            if(flatcarpetarea.containsKey(pos+1))
            {
                flatcarpetarea.remove(pos+1);
            }
        }
        else
        {
            flatcarpetarea.put(pos+1,carpetarea);
        }
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
