package com.icspl.createsoc.VIewpagerEditsoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.WingVPAdaptor;
import com.icspl.createsoc.Model.BlockVPModel;
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
 * {@link WingVP.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WingVP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WingVP extends Fragment implements WingVPAdaptor.WingHandler {
    View v;
    public TextView tv_wingenter;
    public EditText et_enterwings;
    public RecyclerView rv_wings;
    public List<WingVPModel> list;
    public WingVPAdaptor wingVPAdaptor;
    public Button btn_Savewingdetails;
    public Spinner blockspinner;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public HashMap<Integer,String> wings= new HashMap<Integer, String>();
    private List<GetblockidModel> blockmodel= new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WingVP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WingVP.
     */
    // TODO: Rename and change types and number of parameters
    public static WingVP newInstance(String param1, String param2) {
        WingVP fragment = new WingVP();
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
        v= inflater.inflate(R.layout.fragment_wing_v, container, false);
        final List<String> blockid= new ArrayList<>();
        tv_wingenter=v.findViewById(R.id.tv_wingenter);
        et_enterwings=v.findViewById(R.id.et_enterwings);
        rv_wings=v.findViewById(R.id.rv_wings);
        btn_Savewingdetails=v.findViewById(R.id.btn_Savewingdetails);
        list=new ArrayList<>();
        preferences= getActivity().getSharedPreferences("MyPref", 0);
        editor=preferences.edit();
        blockspinner=v.findViewById(R.id.blockspinner);
        //setting blocks
        blockid.add("Select");
        Common common = new Common();
        common.getAPI().getblock(preferences.getString("S_id","")).enqueue(new Callback<List<GetblockidModel>>() {
            @Override
            public void onResponse(Call<List<GetblockidModel>> call, Response<List<GetblockidModel>> response) {
                if(response.body().size()>0)
                {
                    blockmodel=response.body();
                    for (int i = 0; i <response.body().size() ; i++) {
                       blockid.add(response.body().get(i).getBlockName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, blockid);
                    blockspinner.setAdapter(adapter);
                }
                else
                {
                    blockid.add("Select");
                    Toast.makeText(getContext(),"no blocks available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetblockidModel>> call, Throwable t) {
                Toast.makeText(getContext(),"block error",Toast.LENGTH_LONG).show();
            }
        });

        et_enterwings.addTextChangedListener(new TextWatcher() {
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
                    rv_wings.setLayoutManager(linearLayoutManager);
                    wingVPAdaptor = new WingVPAdaptor(getActivity(),list,WingVP.this);
                    rv_wings.setAdapter(wingVPAdaptor);
                    Toast.makeText(getContext(), "cannot be null", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(et_enterwings.getText().toString().matches("[0-9]+"))
                    {
                        int number=Integer.parseInt(et_enterwings.getText().toString());
                        list.clear();

                        for (int j = 0; j < number; j++) {
                            WingVPModel wingVPModel = new WingVPModel("");
                            list.add(wingVPModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        rv_wings.setLayoutManager(linearLayoutManager);
                        wingVPAdaptor = new WingVPAdaptor(getActivity(),list,WingVP.this);
                        rv_wings.setAdapter(wingVPAdaptor);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please enter valid number",Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


        btn_Savewingdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();

            }
        });

        return v;



    }

    private void validate() {
        if(et_enterwings.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please enter wings number of wings",Toast.LENGTH_SHORT).show();
        }
        else
        {

            if(blockspinner.getSelectedItem().toString().equals("Select"))
            {
                Toast.makeText(getActivity(),"Please select block",Toast.LENGTH_LONG).show();
                return;
            }

            int number = Integer.parseInt(et_enterwings.getText().toString());
            if(wings.size()!=number)
            {
                Toast.makeText(getActivity(),"Please fill All values",Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 1; i <=number ; i++) {
                Common common = new Common();
                int bposition= blockspinner.getSelectedItemPosition();
                String bid=blockmodel.get(bposition-1).getBlockId();

                final int finalI = i;
                common.getAPI().addwings(preferences.getString("S_id",""),bid,wings.get(i))
                        .enqueue(new Callback<List<ServerResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                                if(response.body().get(0).getServerResponse().equals(1))
                                {
                                    Toast.makeText(getActivity(),"Wings successfully added",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getActivity(),"Fail to add wing "+wings.get(finalI)+" added",Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                                Toast.makeText(getActivity(),"Network error while creating wings",Toast.LENGTH_LONG).show();
                            }
                        });

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
    public void wingnamecallback(Integer pos, String wingname) {
        if(wingname.equals(""))
        {
            if(wings.containsKey(pos+1))
            {
                wings.remove(pos+1);
            }
        }
        else
        {
            wings.put(pos+1,wingname);
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
